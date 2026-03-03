package br.com.utfpr.calculaimc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions // Importado para o teclado
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType // Importado para tipo numérico
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.utfpr.calculaimc_compose.ui.theme.CalculaIMCcomposeTheme
import java.util.Locale // Importado para forçar o formato do ponto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaIMCcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculaIMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(modifier: Modifier = Modifier) {
    var peso by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.0") }
    val focusRequester = remember { FocusRequester() }

    val calculaIMC : () -> Unit = {
        // CORREÇÃO: substitui vírgula por ponto para o parse funcionar sempre
        val pesoValor = peso.replace(',', '.').toDoubleOrNull()
        val alturaValor = altura.replace(',', '.').toDoubleOrNull()

        if (pesoValor != null && alturaValor != null && alturaValor > 0) {
            val imc = pesoValor / (alturaValor * alturaValor)
            // CORREÇÃO: Força Locale.US para garantir que a String use ponto (ex: "24.80")
            // Isso evita o erro no .toDouble() do IF abaixo
            resultado = String.format(Locale.US, "%.2f", imc)
        }
    }

    val limpar : () -> Unit = {
        peso = ""
        altura = ""
        resultado = "0.0"
        focusRequester.requestFocus()
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso em KG") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            // ADICIONADO: Define o teclado como numérico decimal
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura em metros") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            // ADICIONADO: Define o teclado como numérico decimal
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        // CORREÇÃO: Uso de toDoubleOrNull para evitar crash na interface
        val valorResultado = resultado.toDoubleOrNull() ?: 0.0
        if (valorResultado > 0) {
            PanelResult(resultado = resultado)
        }

        PanelButtons(
            onCalcularClick = calculaIMC,
            onLimparClick = limpar,
            modifier = Modifier
        )
    }
}

@Composable
fun PanelResult(resultado: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resultado",
            modifier = Modifier.padding(all = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = resultado,
            modifier = Modifier.padding(all = 8.dp),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun PanelButtons(
    onCalcularClick: () -> Unit,
    onLimparClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onCalcularClick,
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f)
        ) {
            Text("Calcular")
        }
        Button(
            onClick = onLimparClick,
            modifier = Modifier
                .padding(all = 8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Limpar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculaIMCScreenPreview() {
    CalculaIMCcomposeTheme {
        CalculaIMCScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PanelResultPreview() {
    CalculaIMCcomposeTheme {
        PanelResult(resultado = "24.80")
    }
}

@Preview(showBackground = true)
@Composable
fun PanelButtonsPreview() {
    CalculaIMCcomposeTheme {
        PanelButtons(
            onCalcularClick = {},
            onLimparClick = {}
        )
    }
}