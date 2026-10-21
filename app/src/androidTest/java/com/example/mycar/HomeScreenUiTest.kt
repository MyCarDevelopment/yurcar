package com.example.mycar

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mycar.ui.CardType
import com.example.mycar.ui.Story
import com.example.mycar.ui.home.HomeScreenContent
import com.example.mycar.ui.home.StoryViewer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.FileOutputStream

class HomeScreenUiTest {
    @get:Rule val compose = createComposeRule()

    @Test fun homeDestinationsAndTelegramRemainReachable() {
        var selected: CardType? = null
        var telegramOpened = false
        compose.setContent {
            HomeScreenContent(emptyMap(), { selected = it }, { telegramOpened = true }, {})
        }
        compose.onNodeWithText("Всё для дороги").assertIsDisplayed()
        capture("home-top.png")
        compose.onNodeWithText("Открыть справочник").performClick()
        compose.runOnIdle { assertEquals(CardType.BUY_CAR, selected) }
        compose.onNodeWithText("Товары").performScrollTo().performClick()
        compose.runOnIdle { assertEquals(CardType.SIXTH, selected) }
        compose.onNodeWithText("Вступить в сообщество").performScrollTo().performClick()
        compose.runOnIdle { assertTrue(telegramOpened) }
        capture("home-bottom.png")
    }

    @Test fun storiesOpenAtSelectedIndexAndNavigateBothWays() {
        compose.mainClock.autoAdvance = false
        val stories = listOf(
            Story("Первая", R.drawable.news, R.drawable.st2),
            Story("Вторая", R.drawable.newsf, R.drawable.st3),
            Story("Третья", R.drawable.newss, R.drawable.st4)
        )
        var dismissed = false
        compose.setContent { StoryViewer(stories, 1, { dismissed = true }) }
        compose.onNodeWithText("2 из 3").assertIsDisplayed()
        compose.onNodeWithContentDescription("Предыдущая сторис").performClick()
        compose.mainClock.advanceTimeByFrame()
        compose.onNodeWithText("1 из 3").assertIsDisplayed()
        compose.onNodeWithContentDescription("Следующая сторис").performClick()
        compose.mainClock.advanceTimeByFrame()
        compose.onNodeWithText("2 из 3").assertIsDisplayed()
        capture("story-viewer.png")
        compose.onNodeWithContentDescription("Закрыть сторис").performClick()
        compose.runOnIdle { assertTrue(dismissed) }
    }

    private fun capture(name: String) {
        compose.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        val directory = File(instrumentation.targetContext.getExternalFilesDir(null), "design-review")
        directory.mkdirs()
        FileOutputStream(File(directory, name)).use {
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it)
        }
        bitmap.recycle()
    }
}
