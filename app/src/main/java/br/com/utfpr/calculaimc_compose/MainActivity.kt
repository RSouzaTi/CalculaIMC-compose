package br.com.utfpr.calculaimc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.utfpr.calculaimc_compose.model.imcViewModel
import br.com.utfpr.calculaimc_compose.ui.theme.CalculaIMCcomposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaIMCcomposeTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            route = "home",
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            }
                        ) {
                            CalculaIMCScreen(
                                onNavigateToDeveloper = {
                                    navController.navigate("developer")
                                }
                            )
                        }
                        composable(
                            route = "developer",
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(700)
                                )
                            }
                        ) {
                            DeveloperScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(
    modifier: Modifier = Modifier,
    viewModel: imcViewModel = viewModel(),
    onNavigateToDeveloper: () -> Unit = {}
) {

    val peso = viewModel.peso
    val altura = viewModel.altura
    val resultado = viewModel.resultado

    val focusRequester = remember { FocusRequester() }


    Column(modifier = modifier) {
        OutlinedTextField(
            value = peso,
            onValueChange = { viewModel.onPesoChange(novoPeso = it) },
            label = { Text("Peso em KG") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = altura,
            onValueChange = { viewModel.onAlturaChange(novaAltura = it) },
            label = { Text("Altura em metros") },
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        if (resultado.toDoubleOrNull() ?: 0.0 > 0) {
            PanelResult(resultado = resultado)
        }

        PanelButtons(
            onCalcularClick = { viewModel.calculaIMC() },
            onLimparClick = {
                viewModel.limpar()
                focusRequester.requestFocus()
            },
            modifier = Modifier
        )
        Button(
            onClick = onNavigateToDeveloper,
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Sobre o Desenvolvedor")
        }
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
        CalculaIMCScreen(
            onNavigateToDeveloper = {}
        )
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

@Composable
fun DeveloperScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Desenvolvido por:")
        Text(
            text = "posmoveis-pb@utfpr.edu.br",
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = onBack, modifier = Modifier.padding(top = 20.dp)) {
            Text("Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperScrenPreview() {
    CalculaIMCcomposeTheme {
        DeveloperScreen()
    }
}
