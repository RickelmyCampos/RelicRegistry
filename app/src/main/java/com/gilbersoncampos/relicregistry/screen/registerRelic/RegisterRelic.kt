package com.gilbersoncampos.relicregistry.screen.registerRelic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun RegisterRelic() {
    RegisterRelicUi()
}

@Composable
fun RegisterRelicUi() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Qual o nome do artefato")
        OutlinedTextField(value = "", onValueChange = {})
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterRelicUiPreview() {
    RelicRegistryTheme {
        RegisterRelicUi()
    }
}