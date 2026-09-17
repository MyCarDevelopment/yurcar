package com.example.mycar

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycar.ui.LegalDocument
import com.example.mycar.ui.home.HomeColors

class LegalDocumentsActivity : AppCompatActivity() {

    private val documents = listOf(
        LegalDocument(
            title = "Пользовательское соглашение",
            fileName = "agreement.txt",
            description = "Правила использования мобильного приложения"
        ),
        LegalDocument(
            title = "Политика конфиденциальности",
            fileName = "polkonf.txt",
            description = "Политика в отношении обработки персональных данных"
        ),
        LegalDocument(
            title = "Согласие на обработку персональных данных 1",
            fileName = "numone.txt",
            description = "Согласие пользователя на предоставление данных"
        ),
        LegalDocument(
            title = "Согласие на обработку персональных данных 2",
            fileName = "numtwo.txt",
            description = "Согласие пользователя на предоставление данных"
        ),
        LegalDocument(
            title = "Согласие на обработку персональных данных 3",
            fileName = "numthree.txt",
            description = "Согласие пользователя на предоставление данных"
        ),
        LegalDocument(
            title = "Согласие на обработку персональных данных 4",
            fileName = "numfour.txt",
            description = "Согласие пользователя на предоставление данных"
        ),
        LegalDocument(
            title = "Согласие на обработку персональных данных 5",
            fileName = "numfive.txt",
            description = "Согласие пользователя на предоставление данных"
        ),
        LegalDocument(
            title = "Согласие на обработку персональных данных 6",
            fileName = "numsix.txt",
            description = "Согласие пользователя на предоставление данных"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            title = "Юридическая документация"
            setDisplayHomeAsUpEnabled(true)
        }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = HomeColors.darkBackground
                ) {
                    LegalDocumentsScreen(
                        documents = documents,
                        onDocumentClick = { document ->
                            startActivity(
                                Intent(this, DocumentViewerActivity::class.java).apply {
                                    putExtra("title", document.title)
                                    putExtra("fileName", document.fileName)
                                }
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
@Composable
fun LegalDocumentsScreen(
    documents: List<LegalDocument>,
    onDocumentClick: (LegalDocument) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "Документы",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(documents) { document ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDocumentClick(document) },
                shape = RoundedCornerShape(14.dp),
                elevation = 6.dp,
                backgroundColor = HomeColors.graphite
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = document.title,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = document.description,
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Открыть",
                        tint = HomeColors.accentOrange
                    )
                }
            }
        }
    }
}