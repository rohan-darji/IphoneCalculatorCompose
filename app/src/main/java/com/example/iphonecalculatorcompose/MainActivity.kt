package com.example.iphonecalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.iphonecalculatorcompose.ui.IphoneCalcComponent
import com.example.iphonecalculatorcompose.ui.theme.IphoneCalculatorComposeTheme
import com.example.iphonecalculatorcompose.viewmodel.IphoneCalcViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: IphoneCalcViewModel = IphoneCalcViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IphoneCalculatorComposeTheme{
                IphoneCalcComponent(viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IphoneCalculatorPreview() {
    val viewModel = IphoneCalcViewModel()
    IphoneCalculatorComposeTheme {
        IphoneCalcComponent(viewModel)
    }
}