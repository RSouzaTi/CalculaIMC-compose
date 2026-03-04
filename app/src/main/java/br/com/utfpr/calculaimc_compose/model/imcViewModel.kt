package br.com.utfpr.calculaimc_compose.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class imcViewModel : ViewModel() {

    var peso by mutableStateOf("")
        private set
    var altura by mutableStateOf("")
        private set
    var resultado by mutableStateOf("0.0")
        private set

    fun onPesoChange(novoPeso: String) {
        peso = novoPeso
    }

    fun onAlturaChange(novaAltura: String) {
        altura = novaAltura

    }

fun calculaIMC() {
    val pesoValor = peso.toDoubleOrNull()
    val alturaValor = altura.toDoubleOrNull()

    if (pesoValor != null && alturaValor != null ) {
        val imc = pesoValor / (alturaValor * alturaValor)
        resultado = String.format("%.2f", imc)
    }
}

fun limpar () {
    peso = ""
    altura = ""
    resultado = "0.0"
 }
}