package com.gilbersoncampos.relicregistry.ui.components

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun AlertDialogCustom(title:String,text:String, onDismiss:()->Unit, onConfirm:()->Unit) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = {  },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Não")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Sim")
            }
        })
}

@Composable
@Preview(showBackground = true)
fun AlertDialogPreview() {
    RelicRegistryTheme {
        AlertDialogCustom("TESTE","Teste",{},{})
    }
}