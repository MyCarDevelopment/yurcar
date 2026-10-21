package com.example.mycar

import com.example.mycar.ui.login.*
import org.junit.Assert.*
import org.junit.Test

class YandexRedirectTest {
    private val nonce = "test-state"
    private val callback = "https://oauth.yandex.ru/verification_code"

    @Test fun validCallbackDecodesTokenAndExpiry() {
        val result = parseYandexRedirect("$callback#access_token=test%2Dtoken&expires_in=3600&state=$nonce", nonce)
        assertEquals(YandexRedirect.Success("test-token", 3600L), result)
    }
    @Test fun tokenInAnUnrelatedAddressIsNotAccepted() {
        assertNull(parseYandexRedirect("https://other.example/path#access_token=x&state=$nonce", nonce))
        assertNull(parseYandexRedirect("https://oauth.yandex.ru.other.example/verification_code#access_token=x&state=$nonce", nonce))
        assertNull(parseYandexRedirect("http://oauth.yandex.ru/verification_code#access_token=x&state=$nonce", nonce))
    }
    @Test fun missingAndWrongStateAreRejected() {
        assertEquals(YandexRedirect.Rejected, parseYandexRedirect("$callback#access_token=x", nonce))
        assertEquals(YandexRedirect.Rejected, parseYandexRedirect("$callback#access_token=x&state=wrong", nonce))
    }
    @Test fun duplicatedParametersAndErrorsAreRejected() {
        assertEquals(YandexRedirect.Rejected, parseYandexRedirect("$callback#access_token=x&state=$nonce&state=$nonce", nonce))
        assertEquals(YandexRedirect.Rejected, parseYandexRedirect("$callback#error=access_denied&state=$nonce", nonce))
    }
    @Test fun blankTokensAndWrongCallbackPathsAreRejected() {
        assertEquals(YandexRedirect.Rejected, parseYandexRedirect("$callback#access_token=%20&state=$nonce", nonce))
        assertNull(parseYandexRedirect("https://oauth.yandex.ru/another#access_token=x&state=$nonce", nonce))
    }
}
