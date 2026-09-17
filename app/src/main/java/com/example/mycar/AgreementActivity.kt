package com.example.mycar

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AgreementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showAgreementDialog()
    }

    private fun showAgreementDialog() {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 32, 48, 16)
        }

        val checkBox1 = CheckBox(this)
        val checkBox2 = CheckBox(this)
        val checkBox3 = CheckBox(this)

        val text1 = createAgreementText(
            fullText = "Я принимаю Пользовательское соглашение",
            linkText = "Пользовательское соглашение",
            assetFileName = "agreement.txt"
        )

        val text2 = createAgreementText(
            fullText = "Я принимаю Политику конфиденциальности",
            linkText = "Политику конфиденциальности",
            assetFileName = "privacy.txt"
        )

        val text3 = createAgreementText(
            fullText = "Я принимаю Правила обработки данных",
            linkText = "Правила обработки данных",
            assetFileName = "rules.txt"
        )

        container.addView(createRow(checkBox1, text1))
        container.addView(createRow(checkBox2, text2))
        container.addView(createRow(checkBox3, text3))

        val dialog = AlertDialog.Builder(this)
            .setTitle("Подтверждение")
            .setView(container)
            .setCancelable(false)
            .setPositiveButton("Подтвердить", null)
            .setNegativeButton("Отмена") { _, _ -> finish() }
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false

            val updateState = {
                positiveButton.isEnabled =
                    checkBox1.isChecked && checkBox2.isChecked && checkBox3.isChecked
            }

            checkBox1.setOnCheckedChangeListener { _, _ -> updateState() }
            checkBox2.setOnCheckedChangeListener { _, _ -> updateState() }
            checkBox3.setOnCheckedChangeListener { _, _ -> updateState() }

            positiveButton.setOnClickListener {
                dialog.dismiss()
                finish()
            }
        }

        dialog.show()
    }

    private fun createRow(checkBox: CheckBox, textView: TextView): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 16, 0, 16)

            addView(checkBox)

            addView(
                textView.apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }
            )
        }
    }

    private fun createAgreementText(
        fullText: String,
        linkText: String,
        assetFileName: String
    ): TextView {
        val spannable = SpannableString(fullText)
        val start = fullText.indexOf(linkText)
        val end = start + linkText.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showDocumentDialog(linkText, assetFileName)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.BLUE
                ds.isUnderlineText = true
            }
        }

        spannable.setSpan(
            clickableSpan,
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return TextView(this).apply {
            text = spannable
            textSize = 16f
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    private fun showDocumentDialog(title: String, assetFileName: String) {
        val documentText = assets.open(assetFileName).bufferedReader().use { it.readText() }

        val textView = TextView(this).apply {
            text = documentText
            textSize = 14f
            setPadding(32, 32, 32, 32)
        }

        val scrollView = ScrollView(this).apply {
            addView(textView)
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(scrollView)
            .setPositiveButton("Закрыть", null)
            .show()
    }
}