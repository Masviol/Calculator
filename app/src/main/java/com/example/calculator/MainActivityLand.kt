package com.example.calculator

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivityLand : ComponentActivity() {
    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView

    private var currentExpression = ""

    lateinit var outState: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expressionTextView = findViewById(R.id.expression)
        resultTextView = findViewById(R.id.result)

        setButtonClickListeners()

        clearExpression()

        val tmpExpression: String? = savedInstanceState?.getString("expression")

        if (tmpExpression != null) {
            currentExpression = tmpExpression
            updateExpressionTextView()
        }
    }

    private fun setButtonClickListeners() {
        val buttonIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
            R.id.button_dot, R.id.button_addition, R.id.button_substraction,
            R.id.button_multiply, R.id.button_division, R.id.button_bracket_l,
            R.id.button_bracket_r, R.id.button_clear, R.id.button_back, R.id.button_equals
        )

        for (buttonId in buttonIds) {
            findViewById<TextView>(buttonId).setOnClickListener {
                onButtonClick(findViewById<TextView>(buttonId))
            }
        }
    }

    private fun onButtonClick(button: TextView) {
        val buttonText = button.text.toString()

        // Обработка нажатия кнопки в соответствии с вашей логикой
        when (buttonText) {
            "=" -> calculateResult()
            "C" -> clearExpression()
            "⌫" -> backspace()
            else -> appendToExpression(buttonText)
        }
    }

    private fun calculateResult() {


    }

    private fun clearExpression() {
        // Очистка текущего выражения
        currentExpression = ""
        updateExpressionTextView()
    }

    private fun backspace() {
        // Удаление последнего символа из текущего выражения
        if (currentExpression.isNotEmpty()) {
            currentExpression = currentExpression.substring(0, currentExpression.length - 1)
            updateExpressionTextView()
        }
    }

    private fun appendToExpression(text: String) {
        // Добавление текста к текущему выражению
        currentExpression += text
        updateExpressionTextView()
    }

    private fun updateExpressionTextView() {
        // Обновление TextView с текущим выражением
        expressionTextView.text = currentExpression
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("expression", expressionTextView.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}