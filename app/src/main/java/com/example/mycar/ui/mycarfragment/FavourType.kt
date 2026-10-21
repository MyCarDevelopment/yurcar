package com.example.mycar.ui.mycarfragment

import androidx.compose.runtime.Composable
import com.example.mycar.ui.mycarfragment.GuideHubScreen

@Composable
fun MyCarScreen(
    onSymptomsClick: () -> Unit,
    onLocationClick: () -> Unit,
    onTiresClick: () -> Unit,
    onCriticalityClick: () -> Unit,
    onQuickSolutionClick: () -> Unit,
    onAccidentClick: () -> Unit,
    onBack: () -> Unit = {},
    onTipsClick: () -> Unit = {}
) {
    GuideHubScreen(onBack, onSymptomsClick, onLocationClick, onTiresClick,
        onCriticalityClick, onQuickSolutionClick, onAccidentClick, onTipsClick)
}
