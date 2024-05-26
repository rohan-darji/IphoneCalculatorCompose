package com.example.iphonecalculatorcompose.model

enum class Operation (
    val symbol: String
) {
    NONE(""),
    C("C"),
    PLUS_MINUS("+/-"),
    PERCENTAGE("%"),
    DIVISION("/"),
    MULTIPLICATION("x"),
    SUBTRACTION("-"),
    ADDITION("+"),
    DECIMAL("."),
    EQUALS("="),
}