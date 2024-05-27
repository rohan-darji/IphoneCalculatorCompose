package com.example.iphonecalculatorcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iphonecalculatorcompose.model.Operation
import com.example.iphonecalculatorcompose.ui.theme.AlmostBlack
import com.example.iphonecalculatorcompose.ui.theme.Black
import com.example.iphonecalculatorcompose.ui.theme.Gray
import com.example.iphonecalculatorcompose.ui.theme.Orange
import com.example.iphonecalculatorcompose.ui.theme.White

@Composable
fun WorkingsTVComponent(mutableValueState: State<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = mutableValueState.value,
            textAlign = TextAlign.End,
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = White,
            maxLines = 2,
        )
    }
}

@Composable
fun KeyboardComponent(
    modifier: Modifier = Modifier,
    buttonState: Operation,
    onNumberChange: (Int) -> Unit,
    onOperatorClick: (Operation) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        FunctionButton(
            button = Operation.C
        ) {
            onOperatorClick(Operation.C)
        }
        FunctionButton(
            button = Operation.PLUS_MINUS
        ) {
            onOperatorClick(Operation.PLUS_MINUS)
        }
        FunctionButton(
            button = Operation.PERCENTAGE,
            onClick = onOperatorClick
        )
        OperatorButton(
            operation = Operation.DIVISION,
            onClick = onOperatorClick
        )
    }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        NumberButton(
            text = "7",
            onClick = onNumberChange
        )
        NumberButton(
            text = "8",
            onClick = onNumberChange
        )
        NumberButton(
            text = "9",
            onClick = onNumberChange
        )
        OperatorButton(
            operation = Operation.MULTIPLICATION,
            onClick = onOperatorClick
        )
    }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        NumberButton(
            text = "4",
            onClick = onNumberChange
        )
        NumberButton(
            text = "5",
            onClick = onNumberChange
        )
        NumberButton(
            text = "6",
            onClick = onNumberChange
        )
        OperatorButton(
            operation = Operation.SUBTRACTION,
            onClick = onOperatorClick
        )
    }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        NumberButton(
            text = "1",
            onClick = onNumberChange
        )
        NumberButton(
            text = "2",
            onClick = onNumberChange
        )
        NumberButton(
            text = "3",
            onClick = onNumberChange
        )
        OperatorButton(
            operation = Operation.ADDITION,
            onClick = onOperatorClick
        )
    }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        NumberButton(
            text = "0",
            onClick = onNumberChange,
            sizeModifier = Modifier.size(width = 192.dp, height = 95.dp)
        )
        DecimalButton(
            button = Operation.DECIMAL
        ) {
            onOperatorClick(Operation.DECIMAL)
        }
        OperatorButton(
            operation = Operation.EQUALS,
            onClick = onOperatorClick,
        )
    }
}

@Composable
fun FunctionButton(
    button: Operation,
    onClick: (Operation) -> Unit = { }
) {
    CalcButton(
        text = button.symbol,
        textColor = Black,
        backgroundColor = Gray,
        onClick = {
            onClick.invoke(button)
        }
    )
}

@Composable
fun OperatorButton(
    operation: Operation,
    onClick: (Operation) -> Unit = { }
) {
    CalcButton(
        text = operation.symbol,
        textColor = White,
        backgroundColor = Orange,
        onClick = {
            onClick.invoke(operation)
        }
    )
}

@Composable
fun DecimalButton(
    button: Operation,
    onClick: (Operation) -> Unit = { }
) {
    CalcButton(
        text = button.symbol,
        textColor = White,
        backgroundColor = AlmostBlack,
        onClick = {
            onClick.invoke(button)
        }
    )
}

@Composable
fun NumberButton(
    text: String,
    onClick: (Int) -> Unit = {},
    sizeModifier: Modifier = Modifier.size(width = 95.dp, height = 95.dp),
) {
    CalcButton(
        text = text,
        textColor = White,
        backgroundColor = AlmostBlack,
        onClick = {
            onClick(text.toInt())
        },
        sizeModifier = sizeModifier
    )
}

@Composable
fun CalcButton(
    text: String,
    textColor: Color,
    sizeModifier: Modifier = Modifier.size(width = 95.dp, height = 95.dp),
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple()
            ) {
                onClick()
            }.then(sizeModifier)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 30.sp,
        )
    }
}

