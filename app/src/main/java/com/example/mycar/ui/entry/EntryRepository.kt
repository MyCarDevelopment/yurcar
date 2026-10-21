package com.example.mycar.ui.entry

import android.content.Context
import android.util.AtomicFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.security.MessageDigest
import java.util.UUID

internal fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256").digest(bytes)
    .joinToString("") { "%02x".format(it.toInt() and 255) }

data class SessionReference(val provider: String, val subjectKey: String)

class SessionRepository(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("mycar_auth", Context.MODE_PRIVATE)

    fun current(): SessionReference? {
        val token = prefs.getString("yandex_token", null).orEmpty()
        if (!prefs.getBoolean("is_logged_in", false) || token.isBlank()) return null
        val expiry = prefs.getLong("expires_at", 0L)
        if (expiry > 0 && expiry <= System.currentTimeMillis()) return null
        // No backend subject is available yet. Bind receipts to this session, without storing its token in them.
        return SessionReference("yandex", "yandex-session:" + sha256(token.toByteArray(Charsets.UTF_8)))
    }
}

class ConsentRepository(context: Context) {
    private val app = context.applicationContext

    suspend fun documents(): List<DocumentSnapshot> = withContext(Dispatchers.IO) {
        entryDocuments.map { document ->
            val bytes = app.assets.open(document.fileName).use { it.readBytes() }
            DocumentSnapshot(document, sha256(bytes), bytes.toString(Charsets.UTF_8))
        }
    }

    private fun receiptFile(subjectKey: String): AtomicFile {
        val directory = File(app.filesDir, "consent_receipts")
        if (!directory.exists() && !directory.mkdirs()) throw IOException("Cannot create receipt directory")
        return AtomicFile(File(directory, sha256(subjectKey.toByteArray(Charsets.UTF_8)) + ".json"))
    }

    suspend fun isCurrent(session: SessionReference): Boolean = withContext(Dispatchers.IO) {
        val hashes = documents().filter { it.document.required }.associate { it.document.id to it.sha256 }
        val receipt = try { decode(JSONObject(receiptFile(session.subjectKey).readFully().toString(Charsets.UTF_8))) }
        catch (_: IOException) { null }
        catch (_: org.json.JSONException) { null }
        isConsentReceiptCurrent(receipt, session.subjectKey, hashes)
    }

    suspend fun save(session: SessionReference, selected: Set<String>, snapshots: List<DocumentSnapshot>): ConsentReceipt = withContext(Dispatchers.IO) {
        require(requiredConsentsSelected(selected))
        require(snapshots.map { it.document.id }.toSet() == entryDocuments.map { it.id }.toSet())
        val receipt = ConsentReceipt(UUID.randomUUID().toString(), session.subjectKey, session.provider,
            System.currentTimeMillis(), app.packageManager.getPackageInfo(app.packageName, 0).versionName.orEmpty(), snapshots.map {
                ConsentDecision(it.document.id, it.sha256, it.document.id in selected, it.document.decisionType)
            })
        val target = receiptFile(session.subjectKey)
        val stream = target.startWrite()
        try { stream.write(encode(receipt).toString(2).toByteArray(Charsets.UTF_8)); target.finishWrite(stream) }
        catch (failure: Exception) { target.failWrite(stream); throw failure }
        receipt
    }

    private fun encode(receipt: ConsentReceipt): JSONObject = JSONObject().apply {
        put("schema_version", receipt.schemaVersion)
        put("receipt_id", receipt.id)
        put("subject_key", receipt.subjectKey)
        put("subject_kind", "session_fingerprint")
        put("provider", receipt.provider)
        put("accepted_at_epoch_ms", receipt.acceptedAtMillis)
        put("app_version", receipt.appVersion)
        put("delivery_status", "local_only")
        put("decisions", JSONArray().apply {
            receipt.decisions.forEach { decision -> put(JSONObject().apply {
                put("document_id", decision.documentId); put("document_sha256", decision.sha256)
                put("accepted", decision.accepted); put("decision_type", decision.type)
            }) }
        })
    }

    private fun decode(json: JSONObject): ConsentReceipt {
        val decisions = json.getJSONArray("decisions")
        return ConsentReceipt(json.getString("receipt_id"), json.getString("subject_key"), json.getString("provider"),
            json.getLong("accepted_at_epoch_ms"), json.getString("app_version"),
            (0 until decisions.length()).map { index -> decisions.getJSONObject(index).let {
                ConsentDecision(it.getString("document_id"), it.getString("document_sha256"), it.getBoolean("accepted"), it.getString("decision_type"))
            } }, json.getInt("schema_version"))
    }
}
