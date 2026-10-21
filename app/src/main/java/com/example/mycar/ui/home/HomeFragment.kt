package com.example.mycar.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.mycar.LegalDocumentsActivity
import com.example.mycar.R
import com.example.mycar.ui.CardType
import com.example.mycar.ui.checkfines.FinesFragment
import com.example.mycar.ui.indicators.IndicatorsActivity
import com.example.mycar.ui.instructions.InstructionsActivity
import com.example.mycar.ui.marketplace.MarketplaceFragment
import com.example.mycar.ui.mycarfragment.MyCarActivity
import com.example.mycar.ui.services.ServicesActivity

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeScreenContent(mapOf(CardType.BUY_CAR to R.drawable.test),
                    onCardClick = ::openCard,
                    onTelegramClick = { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/mycarcommunity"))) },
                    onOpenLegalDocuments = { startActivity(Intent(requireContext(), LegalDocumentsActivity::class.java)) })
            }
        }

    private fun openCard(card: CardType) {
        val target = when (card) {
            CardType.BUY_CAR -> MyCarActivity::class.java
            CardType.SELL_CAR -> FinesFragment::class.java
            CardType.TIRES -> IndicatorsActivity::class.java
            CardType.SERVICES -> ServicesActivity::class.java
            CardType.INSTRUCTIONS -> InstructionsActivity::class.java
            CardType.SIXTH -> MarketplaceFragment::class.java
        }
        startActivity(Intent(requireContext(), target))
    }
}

// Kept for existing screens that share this palette.
object HomeColors {
    val darkBackground = Color(0xFF1E2428)
    val darkGray = Color(0xFF262D33)
    val graphite = Color(0xFF2E3840)
    val accentOrange = Color(0xFF343F48)
}
