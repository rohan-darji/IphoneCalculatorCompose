package com.example.iphonecalculatorcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.iphonecalculatorcompose.ui.theme.Black
import com.example.iphonecalculatorcompose.viewmodel.CalculatorEvent
import com.example.iphonecalculatorcompose.viewmodel.IphoneCalcViewModel

@Composable
fun IphoneCalcComponent(viewModel: IphoneCalcViewModel) {
    val operatorState = viewModel.buttonState.collectAsState()
    val entryState = viewModel.entryState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            WorkingsTVComponent(
                entryState
            )
            KeyboardComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                buttonState = operatorState.value,
                onNumberChange = { entry: Int ->
                    viewModel.onEvent(CalculatorEvent.Number(entry))
                }, onOperatorClick = { operation ->
                    viewModel.onEvent(CalculatorEvent.Calculation(operation))
                }
            )
        }
    }
}