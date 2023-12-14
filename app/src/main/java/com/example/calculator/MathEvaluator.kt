package com.example.calculator

import java.util.Stack
import kotlin.math.ln
import kotlin.math.sqrt

class MathEvaluator {
    fun ConvertExpression(expression: String): String {
        var convertedExpression = StringBuilder()

        if (!expression[0].isDigit()) return "ERROR"

        for (index in expression.indices) {
            when {
                expression[index].isDigit() -> convertedExpression.append(expression[index])
                expression[index] == 'l' -> convertedExpression.append('l')
                expression[index] == '√' -> convertedExpression.append('l')
                !expression[index].isDigit() -> {
                    if (!expression[index - 1].isDigit()) return "ERROR"
                    if (expression[index -1].isDigit()) convertedExpression.append('f')
                    convertedExpression.append(expression[index])
                }
            }
        }

        return convertedExpression.toString()
    }

    fun MakeRPN(expression: String): String {
        if (expression == "ERROR") return "ERROR"

        val output = StringBuilder()
        val stack = Stack<Char>()

        for(symbol in expression) {
            when {
                symbol == 'f' -> output.append(symbol)
                symbol.isDigit() || symbol == '.' -> output.append(symbol)
                symbol == '√' -> stack.push(symbol)
                symbol == 'l' -> stack.push(symbol)
                symbol == '(' -> stack.push(symbol)
                symbol == ')' -> {
                    while (stack.peek() != '(') {
                        output.append(stack.pop())
                    }
                    if (stack.isEmpty()) return "ERROR"
                    stack.pop()
                }
                "+-*/".contains(symbol) -> {
                    while (stack.isNotEmpty() && CheckPriority(stack.peek()) >= CheckPriority(symbol)) {
                        output.append(stack.pop())

                    }
                    stack.push(symbol)
                }
            }
        }

        while (stack.isNotEmpty()) {
            output.append(stack.pop())
        }

        return output.toString().trim()
    }

    fun CheckPriority(operator: Char): Int {
        return when (operator) {
            '+', '-' -> 1
            '*', '/' -> 2
            else -> 0
        }
    }

    fun Calculate(originalExpression: String): String {
        val convertedExpression = ConvertExpression(originalExpression)
        if (convertedExpression == "ERROR") return "ERROR"

        val expression = MakeRPN(convertedExpression)
        if (expression == "ERROR") return "ERROR"

        var value = StringBuilder()
        var values = mutableListOf<Float>()
        var result: Float = 0f

        for (index in expression.indices) {
            when {
                expression[index].isDigit() -> value.append(expression[index])
                expression[index] == 'f' -> {
                    values.add(value.toString().toFloat())
                    value.clear()
                }

                expression[index] == '√' -> values[values.size - 1] = sqrt(values[values.size - 1])
                expression[index] == 'l' -> values[values.size - 1] = ln(values[values.size - 1])
                expression[index] == '+' -> {
                    val tmpValue: Float = values[0] + values[1]
                    values.clear()
                    values.add(tmpValue)
                    result = tmpValue
                }

                expression[index] == '-' -> {
                    val tmpValue = values[0] - values[1]
                    values.clear()
                    values.add(tmpValue)
                    result = tmpValue
                }

                expression[index] == '*' -> {
                    if (values[0] == 0f || values[1] == 0f) return "ERROR"
                    val tmpValue = values[0] * values[1]
                    values.clear()
                    values.add(tmpValue)
                    result = tmpValue
                }

                expression[index] == '/' -> {
                    if (values[0] == 0f || values[1] == 0f) return "ERROR"
                    val tmpValue = values[0] / values[1]
                    values.clear()
                    values.add(tmpValue)
                    result = tmpValue
                }
            }
        }
        return result.toString()
    }
}