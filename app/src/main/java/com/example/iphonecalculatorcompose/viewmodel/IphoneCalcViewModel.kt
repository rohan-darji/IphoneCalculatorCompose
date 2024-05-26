package com.example.iphonecalculatorcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iphonecalculatorcompose.model.Calculator
import com.example.iphonecalculatorcompose.model.Operation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IphoneCalcViewModel : ViewModel() {
    private val _mutableEraserState = MutableStateFlow(false)
    private val _mutableMemoryState =  MutableStateFlow<Float?>(null)
    private val _mutableButtonState = MutableStateFlow(Operation.NONE)
    private val _mutableEntryState = MutableStateFlow("")
    private var canAddDecimal = 1


    val buttonState = _mutableButtonState.asStateFlow()
    val entryState= _mutableEntryState.asStateFlow()

    fun onEvent(calculatorEvent: CalculatorEvent) {
        viewModelScope.launch {
            when(calculatorEvent) {
                CalculatorEvent.Equals -> equals()
                CalculatorEvent.AllClear -> allClear()
                is CalculatorEvent.Calculation -> {
                    when (calculatorEvent.operation) {
                        Operation.C -> allClear()
                        Operation.PLUS_MINUS -> changeSign()
                        Operation.DECIMAL -> decimal()
                        Operation.PERCENTAGE -> percentage()
                        Operation.EQUALS -> equals()
                        else -> selectOperator(calculatorEvent.operation)
                    }
                }
                is CalculatorEvent.Number -> {
                    enterNumber(calculatorEvent.value)
                }
            }
        }
    }


    private fun enterNumber(entry: Int) {
        viewModelScope.launch {
            if (_mutableEntryState.value.length < 12) {
                if (_mutableEraserState.value) {
                    _mutableEntryState.value = "0"
                    _mutableEraserState.value = false
                }
                val valueBuilder = StringBuilder()
                if (_mutableEntryState.value != "0") {
                    valueBuilder
                        .append(_mutableEntryState.value)
                }
                valueBuilder.append(entry)
                _mutableEntryState.value = valueBuilder.toString()
            }
        }
    }

    private fun selectOperator(
        button: Operation
    ) {
        if (_mutableEraserState.value.not())  {
            if (_mutableMemoryState.value == null) {
                _mutableMemoryState.value = _mutableEntryState.value.toFloat()
            } else {
                val total = calculation(
                    actual = _mutableMemoryState.value ?: 0f,
                    entry = _mutableEntryState.value.toFloat(),
                    _mutableButtonState.value
                )
                _mutableMemoryState.value = total
                _mutableEntryState.value = total.toString()
            }
            _mutableButtonState.value = button
            _mutableEraserState.value = true
            canAddDecimal = 1
        } else  {
            _mutableButtonState.value = button
        }
    }

    private fun calculation(
        actual: Float,
        entry: Float,
        currentOperation: Operation
    ): Float {
        val calculation = when (currentOperation) {
            Operation.DIVISION -> Calculator.Division(actual, entry)()
            Operation.MULTIPLICATION -> Calculator.Multiplication(actual, entry)()
            Operation.SUBTRACTION -> Calculator.Subtraction(actual, entry)()
            Operation.ADDITION -> Calculator.Addition(actual, entry)()
            Operation.PERCENTAGE -> {
                percentage()
                0f
            }
            Operation.PLUS_MINUS -> {
                changeSign()
                0f
            }
            Operation.DECIMAL -> {
                decimal()
                0f
            }
            else -> 0f
        }
        return calculation
    }

    private fun percentage() {
        val entryValue = _mutableEntryState.value.toFloat()
        val percentageValue = entryValue / 100
        _mutableMemoryState.value = percentageValue
        _mutableEntryState.value = percentageValue.toString()
    }

    private fun changeSign() {
//        Log.d("IphoneCalcViewModel", "changeSign function called")
        val entryValue = _mutableEntryState.value.toFloat()
        val changedSignValue = entryValue * -1
        _mutableMemoryState.value = changedSignValue
        _mutableEntryState.value = changedSignValue.toString()
        canAddDecimal = 1
    }

    private fun decimal() {
        if(canAddDecimal == 1){
            if(_mutableEntryState.value == "") {
                _mutableEntryState.value = "0."
            }
            else if (_mutableEraserState.value) {
                _mutableEntryState.value = "0."
                _mutableEraserState.value = false
            }
            else {
                _mutableEntryState.value += "."
            }
            canAddDecimal = 0
        }
    }

    private fun allClear() {
        _mutableEntryState.value = ""
        _mutableMemoryState.value = null
        canAddDecimal = 1
    }

    private fun equals() {
        val total = calculation(
            _mutableMemoryState.value ?: 0f,
            _mutableEntryState.value.toFloat(),
            _mutableButtonState.value
        )
        _mutableMemoryState.value = total
        _mutableEntryState.value = total.toString()
        _mutableEraserState.value = true
        _mutableButtonState.value = Operation.NONE
        canAddDecimal = 1
    }
}

sealed class CalculatorEvent {
    data class Number(val value: Int) : CalculatorEvent()
    data class Calculation(val operation: Operation): CalculatorEvent()
    object Equals : CalculatorEvent()
    object AllClear : CalculatorEvent()
}