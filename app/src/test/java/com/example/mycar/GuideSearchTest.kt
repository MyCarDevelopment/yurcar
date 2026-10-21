package com.example.mycar

import com.example.mycar.ui.mycarfragment.*
import org.junit.Assert.*
import org.junit.Test

class GuideSearchTest {
    @Test fun searchTreatsYoAndEEquallyAndIgnoresExtraWhitespace() {
        val withYo = findGuideArticles(GuideSection.LOCATION, "колёса")
        val withE = findGuideArticles(GuideSection.LOCATION, "  КОЛЕСА  ")
        assertTrue(withYo.isNotEmpty())
        assertEquals(withYo.map { it.id }, withE.map { it.id })
    }

    @Test fun multipleWordsSearchAcrossTitleAndExplanation() {
        val results = findGuideArticles(GuideSection.SYMPTOMS, "запускается аккумулятора")
        assertEquals(listOf("no-start"), results.map { it.id })
    }

    @Test fun locationAndUrgencyFiltersApplyTogetherWithSearch() {
        assertTrue(findGuideArticles(GuideSection.LOCATION, "стук", "Под капотом").isEmpty())
        assertEquals(listOf("knock"), findGuideArticles(GuideSection.LOCATION, "стук", "Колёса и тормоза").map { it.id })
        val urgent = findGuideArticles(GuideSection.URGENCY, "", GuideUrgency.STOP.label)
        assertTrue(urgent.isNotEmpty())
        assertTrue(urgent.all { it.urgency == GuideUrgency.STOP })
    }

    @Test fun maintenanceDoesNotMixWithDiagnosticArticles() {
        val maintenanceIds = articlesFor(GuideSection.MAINTENANCE).map { it.id }.toSet()
        val symptomsIds = articlesFor(GuideSection.SYMPTOMS).map { it.id }.toSet()
        assertTrue(maintenanceIds.isNotEmpty())
        assertTrue(maintenanceIds.intersect(symptomsIds).isEmpty())
        assertTrue(findGuideArticles(GuideSection.MAINTENANCE, "несуществующийсимптом").isEmpty())
    }
}
