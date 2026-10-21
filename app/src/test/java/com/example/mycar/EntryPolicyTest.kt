package com.example.mycar

import com.example.mycar.ui.entry.*
import org.junit.Assert.*
import org.junit.Test

class EntryPolicyTest {
    private val required = entryDocuments.filter { it.required }
    private val hashes = required.associate { it.id to "revision-${it.id}" }
    private fun receipt(subject: String = "account-A") = ConsentReceipt("receipt-1", subject, "yandex", 1000L, "1.0",
        required.map { ConsentDecision(it.id, hashes.getValue(it.id), true, it.decisionType) })

    @Test fun startupAlwaysKeepsTheExpectedOrder() {
        assertEquals(EntryStep.INTRODUCTION, nextEntryStep(false, true, true))
        assertEquals(EntryStep.AUTHENTICATION, nextEntryStep(true, false, true))
        assertEquals(EntryStep.CONSENTS, nextEntryStep(true, true, false))
        assertEquals(EntryStep.HOME, nextEntryStep(true, true, true))
    }
    @Test fun advertisingAndNewsAreNotRequiredForEntry() {
        val selected = required.map { it.id }.toSet()
        assertTrue(requiredConsentsSelected(selected))
        assertFalse("marketing" in selected)
        assertFalse("information" in selected)
        required.forEach { assertFalse(requiredConsentsSelected(selected - it.id)) }
    }
    @Test fun currentReceiptMatchesAllMandatoryVersions() {
        assertTrue(isConsentReceiptCurrent(receipt(), "account-A", hashes))
        assertFalse(isConsentReceiptCurrent(receipt(), "account-A", hashes + ("terms" to "new-version")))
    }
    @Test fun receiptCannotBeReusedForAnotherSession() {
        assertFalse(isConsentReceiptCurrent(receipt(), "account-B", hashes))
        assertFalse(isConsentReceiptCurrent(null, "account-A", hashes))
    }
    @Test fun declinedOrDuplicateMandatoryDecisionIsRejected() {
        val original = receipt()
        val declined = original.copy(decisions = original.decisions.map { if (it.documentId == "terms") it.copy(accepted = false) else it })
        assertFalse(isConsentReceiptCurrent(declined, "account-A", hashes))
        assertFalse(isConsentReceiptCurrent(original.copy(decisions = original.decisions + original.decisions.first()), "account-A", hashes))
    }
    @Test fun incompletePolicyOrUnsupportedSchemaCannotUnlockHome() {
        assertFalse(isConsentReceiptCurrent(receipt(), "account-A", emptyMap()))
        assertFalse(isConsentReceiptCurrent(receipt().copy(schemaVersion = 99), "account-A", hashes))
    }
}
