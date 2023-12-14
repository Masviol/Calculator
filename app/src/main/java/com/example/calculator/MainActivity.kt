package com.example.calculator


import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var sqrt: TextView
    private lateinit var ln: TextView

    private var currentExpression = ""

    lateinit var outState: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expressionTextView = findViewById(R.id.expression)
        resultTextView = findViewById(R.id.result)
        setButtonClickListeners()
        if (isLandSpaced()) {
            sqrt = findViewById(R.id.button_sqrt)
            sqrt.setOnClickListener{
                onButtonClick(findViewById<TextView>(R.id.button_sqrt))
            }

            ln = findViewById(R.id.button_natural_log)
            ln.setOnClickListener{
                onButtonClick(findViewById<TextView>(R.id.button_natural_log))
            }
        }

        val tmpExpression: String? = savedInstanceState?.getString("expression")
        val tmpResult: String? = savedInstanceState?.getString("result")
        if (tmpExpression != null) {
            currentExpression = tmpExpression
            updateExpressionTextView()
        }
        if (tmpResult != null) resultTextView.text = tmpResult
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

    private fun isLandSpaced(): Boolean {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) return true
        return false
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
        val mathEvaluator = MathEvaluator()
        val result = mathEvaluator.Calculate(expressionTextView.text.toString())
        resultTextView.text = result
    }

    private fun clearExpression() {
        // Очистка текущего выражения
        currentExpression = ""
        updateExpressionTextView()
        resultTextView.text = ""
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
        outState.putString("result", resultTextView.text.toString())
        super.onSaveInstanceState(outState)
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
}


