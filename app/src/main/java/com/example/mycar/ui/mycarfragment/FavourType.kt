import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.outlined.CarRepair
import androidx.compose.material.icons.outlined.DriveEta
import androidx.compose.material.icons.outlined.TireRepair
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppBg = Color(0xFFF2F3F5)
private val CardGray = Color(0xFFE3E5E8)
private val CardGrayDark = Color(0xFFD8DBDF)
private val TextPrimary = Color(0xFF1F2328)
private val TextSecondary = Color(0xFF6B7280)
private val Accent = Color(0xFF4B5563)
private val BorderColor = Color(0xFFD1D5DB)

@Composable
fun MyCarScreen(
    onSymptomsClick: () -> Unit,
    onLocationClick: () -> Unit,
    onTiresClick: () -> Unit,
    onCriticalityClick: () -> Unit,
    onQuickSolutionClick: () -> Unit,
    onAccidentClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Диагностика авто",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Text(
                text = "Выберите удобный способ поиска проблемы",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CarCard(
                    title = "По симптомам",
                    icon = Icons.Outlined.Build,
                    modifier = Modifier.weight(1f),
                    onClick = onSymptomsClick
                )
                CarCard(
                    title = "По местоположению",
                    icon = Icons.Outlined.LocationOn,
                    modifier = Modifier.weight(1f),
                    onClick = onLocationClick
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CarCard(
                    title = "Приборка",
                    icon = Icons.Outlined.Speed,
                    modifier = Modifier.weight(1f),
                    onClick = onTiresClick
                )
                CarCard(
                    title = "Критичность",
                    icon = Icons.Outlined.PriorityHigh,
                    modifier = Modifier.weight(1f),
                    onClick = onCriticalityClick
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CarBigCard(
                title = "Быстрое решение",
                subtitle = "Простые действия, которые можно выполнить самостоятельно",
                icon = Icons.Outlined.CarRepair,
                onClick = onQuickSolutionClick
            )


            //сделать новую карточку
            CarBigCard( 
                title = "Замена расходников",
                subtitle = "Сроки замены расходных материалов в авто",
                icon = Icons.Outlined.DriveEta,
                onClick = onAccidentClick
            )
        }
    }
}

@Composable
fun CarCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(112.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = 0.dp,
        backgroundColor = CardGray,
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = CardGrayDark,
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Accent,
                    modifier = Modifier.size(22.dp)
                )
            }

            Text(
                text = title,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                lineHeight = 18.sp,
                maxLines = 2
            )
        }
    }
}

@Composable
fun CarBigCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = 0.dp,
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = CardGray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Accent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )
            }

            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Открыть",
                tint = TextSecondary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}