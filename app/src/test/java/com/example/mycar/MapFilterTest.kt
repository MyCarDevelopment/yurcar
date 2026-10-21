package com.example.mycar

import com.example.mycar.ui.map.*
import org.junit.Assert.*
import org.junit.Test

class MapFilterTest {
    @Test fun allResetsEveryTypeIncludingPounds() {
        val pounds = filterMapPlaces(mapPlaces, "", setOf(PointType.POUND))
        assertTrue(pounds.isNotEmpty())
        assertTrue(pounds.all { it.type == PointType.POUND })
        assertEquals(mapPlaces, filterMapPlaces(mapPlaces, "", emptySet()))
    }

    @Test fun multipleCategoriesFormAUnion() {
        val types = setOf(PointType.STO, PointType.TIRE)
        val results = filterMapPlaces(mapPlaces, "", types)
        assertEquals(mapPlaces.count { it.type in types }, results.size)
        assertTrue(results.any { it.type == PointType.STO })
        assertTrue(results.any { it.type == PointType.TIRE })
    }

    @Test fun searchMatchesAddressAndServiceRegardlessOfWhitespaceAndCase() {
        val results = filterMapPlaces(mapPlaces, "  САВУШКИНА ремонт  ", emptySet())
        assertEquals(listOf("СТО на Савушкина"), results.map { it.companyName })
    }

    @Test fun queryAndCategoryAreAppliedTogether() {
        assertTrue(filterMapPlaces(mapPlaces, "Школьной", setOf(PointType.TIRE)).isEmpty())
        assertTrue(filterMapPlaces(mapPlaces, "несуществующее место", emptySet()).isEmpty())
    }
}
