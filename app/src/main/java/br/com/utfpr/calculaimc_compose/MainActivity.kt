package br.com.utfpr.calculaimc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.utfpr.calculaimc_compose.ui.theme.CalculaIMCcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaIMCcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculaIMCScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(name: String, modifier: Modifier = Modifier) {

    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Peso:",
            modifier = modifier.padding(top = 48.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso em KG") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()

        )
        Text(
            text = "Altura:",
            modifier = modifier.padding(all = 8.dp)
        )
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura em metros") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Resultado:",
            modifier = modifier.padding(all = 8.dp)
        )
        Text(
            text = "0.0:",
            modifier = modifier.padding(all = 8.dp)
        )
        Row {
            Button(
                onClick = {},
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text("Calcular")
            }
            Button(
                onClick = {},
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text("Limpar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculaIMCScreenPreview() {
    CalculaIMCcomposeTheme {
        CalculaIMCScreen(name = "Android")
    }
}
