package com.example.simplearithmetic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplearithmetic.ui.theme.SimpleArithmeticTheme
import androidx.compose.material3.Text as Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleArithmeticTheme {
                SimpleArithmetic()
            }
        }
    }
}

@Composable
fun SimpleArithmetic() {

    //numbers that will be operated on
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // list of allowed operations, for radio buttons using a list
    val radioOptions = listOf("Addition", "Subtraction", "Multiplication", "Division")
    // holds current state of options and updates state when clicked
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


    Column  (modifier = Modifier.padding(32.dp)) {
        radioOptions.forEach { text ->
            Row(
                Modifier.fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // ui and handler for user inputs
        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Number 1") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Number 2") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // handle calculation and store the result as a string
                result=calculate(num1,num2,selectedOption)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result: $result")
    }

}

fun calculate(num1: String, num2: String, operand: String ): String {


    return try {
        val n1 = num1.toDouble()
        val n2 = num2.toDouble()

        val result = when (operand) {
            "Addition" ->  n1 + n2
            "Subtraction" ->  n1 - n2
            "Multiplication" ->  n1 * n2
            "Division" -> if (n2 == 0.0) "Error can't divide by 0" else n1 / n2
            else -> 0.0
        }
        result.toString()
    } catch (e: NumberFormatException) {
        "Error: Invalid number input"
    } catch (e: ArithmeticException) {
        "Error: Arithmetic exception"
    }
}

@Preview(showBackground = true)
@Composable
fun ArithmeticPreview() {

}