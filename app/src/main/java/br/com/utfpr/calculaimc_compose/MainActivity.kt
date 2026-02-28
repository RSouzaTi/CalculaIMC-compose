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
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    var peso by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.0") }


    val calculaIMC  = {
        val pesoValor = peso.toDoubleOrNull()
        val alturaValor = altura.toDoubleOrNull()

        if (pesoValor != null && alturaValor != null) {
            val imc = pesoValor / (alturaValor * alturaValor)
            resultado = String.format("%.2f", imc)
        }

    }

    Column {

        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso em KG") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()

        )

        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura em metros") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        )
        if (resultado.toDouble() >8 ) {
            PanelResul(resultado = resultado)

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)

        )
        {
            Button(
                onClick = calculaIMC,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
            ) {
                Text("Calcular")
            }
            Button(
                onClick = {peso = ""; altura = ""; resultado = "0.0"},
                modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary)

            ) {
                Text("Limpar")
            }
        }
    }
}
@Composable
fun PanelResul(resultado: String,
               modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "Resultado",
            modifier = Modifier
                .padding(all = 8.dp)
        )
        Text(
            text = resultado,
            modifier = Modifier
                .padding(all = 8.dp),
            style = MaterialTheme.typography.displayLarge
        )
    }

}
@Preview(showBackground = true)
@Composable
fun PanelResulPreview() {
    CalculaIMCcomposeTheme {
        PanelResul(resultado = "24.80")
    }
}


@Preview(showBackground = true)
@Composable
fun CalculaIMCScreenPreview() {
    CalculaIMCcomposeTheme {
        CalculaIMCScreen(name = "Android")
    }
}
