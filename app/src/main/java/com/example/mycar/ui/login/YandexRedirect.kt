package com.example.mycar.ui.login

import java.net.URI
import java.net.URLDecoder

sealed class YandexRedirect {
    data class Success(val token: String, val expiresInSeconds: Long?) : YandexRedirect()
    data object Rejected : YandexRedirect()
}

fun parseYandexRedirect(url: String, expectedState: String): YandexRedirect? {
    val uri = try { URI(url) } catch (_: Exception) { return null }
    if (!uri.scheme.equals("https", true) || !uri.host.equals("oauth.yandex.ru", true) ||
        uri.path != "/verification_code" || uri.rawUserInfo != null || uri.port !in listOf(-1, 443)) return null
    val raw = listOfNotNull(uri.rawQuery, uri.rawFragment).filter { it.isNotEmpty() }.joinToString("&")
    val pairs = try { raw.split('&').filter { it.isNotBlank() }.map { part ->
        val split = part.split('=', limit = 2)
        URLDecoder.decode(split[0], "UTF-8") to URLDecoder.decode(split.getOrElse(1) { "" }, "UTF-8")
    } } catch (_: IllegalArgumentException) { return YandexRedirect.Rejected }
    if (pairs.map { it.first }.distinct().size != pairs.size || expectedState.isBlank()) return YandexRedirect.Rejected
    val values = pairs.toMap()
    if (values["state"] != expectedState || "error" in values) return YandexRedirect.Rejected
    val token = values["access_token"].orEmpty()
    if (token.isBlank() || token.any { it.isWhitespace() || it.isISOControl() }) return YandexRedirect.Rejected
    return YandexRedirect.Success(token, values["expires_in"]?.toLongOrNull()?.takeIf { it in 1..315360000L })
}
