package com.example.iphonecalculatorcompose.model

sealed class Calculator(
    private val oldNumber: Float,
    private val newNumber: Float,
) {
    protected abstract fun performCalculation(oldNumber: Float, newNumber: Float): Float

    operator fun invoke(): Float {
        return performCalculation(oldNumber, newNumber)
    }

    class Addition(actual: Float, plus: Float) : Calculator(actual, plus) {
        override fun performCalculation(oldNumber: Float, newNumber: Float): Float {
            return oldNumber + newNumber
        }
    }

    class Subtraction(actual: Float, subtraction: Float) : Calculator(actual, subtraction) {
        override fun performCalculation(oldNumber: Float, newNumber: Float): Float {
            return oldNumber - newNumber
        }
    }

    class Multiplication(actual: Float, multiplication: Float) : Calculator(actual, multiplication) {
        override fun performCalculation(oldNumber: Float, newNumber: Float): Float {
            return oldNumber * newNumber
        }
    }

    class Division(actual: Float, division: Float) : Calculator(actual, division) {
        override fun performCalculation(oldNumber: Float, newNumber: Float): Float {
            if (newNumber != 0f) {
                return oldNumber / newNumber
            }
            throw IllegalArgumentException("Division by zero is not allowed.")
        }
    }
}