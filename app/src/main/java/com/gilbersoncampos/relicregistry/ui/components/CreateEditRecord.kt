package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel

@Composable
fun CreateEditRecordModal(
    onCreate: (String, String, String, String) -> Unit,
    defaultValues: CatalogRecordModel? = null,
    isEditing: Boolean = false,
    onClose: () -> Unit
) {
    var numbering by remember { mutableStateOf(defaultValues?.identification ?: "") }
    var place by remember { mutableStateOf(defaultValues?.archaeologicalSite ?: "") }
    var shelf by remember { mutableStateOf(defaultValues?.shelfLocation ?: "") }
    var group by remember { mutableStateOf(defaultValues?.group?:"") }

    Dialog(onDismissRequest = onClose) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Criar uma nova ficha",
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    value = numbering,
                    onValueChange = { numbering = it },
                    label = { Text("Numeração") }
                )
                OutlinedTextField(
                    value = place,
                    onValueChange = { place = it },
                    label = { Text("Sítio") }
                )
                OutlinedTextField(
                    value = shelf,
                    onValueChange = { shelf = it },
                    label = { Text("Prateleira") }
                )
                OutlinedTextField(
                    value = group,
                    onValueChange = { group = it },
                    label = { Text("Grupo") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onClose) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onCreate(numbering, place, shelf, group) }) {
                        Text(if (isEditing) "Salvar" else "Criar")
                    }
                }
            }
        }
    }
}