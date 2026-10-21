package com.example.mycar.ui.entry

enum class EntryStep { INTRODUCTION, AUTHENTICATION, CONSENTS, HOME }

fun nextEntryStep(introductionComplete: Boolean, authenticated: Boolean, consentsCurrent: Boolean): EntryStep = when {
    !introductionComplete -> EntryStep.INTRODUCTION
    !authenticated -> EntryStep.AUTHENTICATION
    !consentsCurrent -> EntryStep.CONSENTS
    else -> EntryStep.HOME
}

data class EntryDocument(val id: String, val title: String, val fileName: String,
                         val description: String, val required: Boolean = false,
                         val decisionType: String = "consent")

val entryDocuments = listOf(
    EntryDocument("terms", "Правила использования", "agreement.txt", "Принимаю пользовательское соглашение", true, "acceptance"),
    EntryDocument("privacy", "Конфиденциальность", "polkonf.txt", "Ознакомлен с политикой обработки персональных данных", true, "acknowledgement"),
    EntryDocument("account", "Данные для аккаунта", "numtwo.txt", "Даю согласие на обработку данных для регистрации личного кабинета", true),
    EntryDocument("features", "Данные для работы приложения", "numthree.txt", "Даю согласие на обработку данных для использования функций приложения", true),
    EntryDocument("information", "Новости сервиса", "numfive.txt", "Хочу получать информацию о сервисах и услугах"),
    EntryDocument("marketing", "Рекламные сообщения", "numsix.txt", "Согласен на рекламные коммуникации и рассылки")
)

val allLegalDocuments = entryDocuments + listOf(
    EntryDocument("contract", "Данные для заключения договора", "numone.txt", "Отдельное согласие для подготовки и исполнения договора"),
    EntryDocument("support", "Обращения в поддержку", "numfour.txt", "Отдельное согласие для взаимодействия с поддержкой")
)

data class DocumentSnapshot(val document: EntryDocument, val sha256: String, val text: String)
data class ConsentDecision(val documentId: String, val sha256: String, val accepted: Boolean, val type: String)
data class ConsentReceipt(val id: String, val subjectKey: String, val provider: String,
                          val acceptedAtMillis: Long, val appVersion: String,
                          val decisions: List<ConsentDecision>, val schemaVersion: Int = 1)

fun requiredConsentsSelected(selected: Set<String>): Boolean = entryDocuments.filter { it.required }.all { it.id in selected }

fun isConsentReceiptCurrent(receipt: ConsentReceipt?, subjectKey: String, requiredHashes: Map<String, String>): Boolean {
    if (receipt == null || receipt.schemaVersion != 1 || receipt.subjectKey != subjectKey || receipt.acceptedAtMillis <= 0) return false
    if (requiredHashes.keys != entryDocuments.filter { it.required }.map { it.id }.toSet()) return false
    return requiredHashes.all { (id, hash) -> receipt.decisions.count { it.documentId == id } == 1 &&
        receipt.decisions.any { it.documentId == id && it.sha256 == hash && it.accepted } }
}
