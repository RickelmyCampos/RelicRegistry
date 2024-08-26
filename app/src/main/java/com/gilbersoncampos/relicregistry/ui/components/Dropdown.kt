package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gilbersoncampos.relicregistry.data.model.DropdownData
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme



@Composable
fun CustomDropdown(
    list: List<String>,
    selectedState: String?,
    onSelect: (String) -> Unit,
    title: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }
//    OutlinedButton(
//        onClick = { expanded = !expanded },
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(4.dp)
//        ) {
//        Text(text = selectedState?.name ?: "Selecione uma opção")
//    }
    Button(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        ) {
        Text(text = selectedState?: "Selecione uma opção")
    }
    RenderDropDownModal(
        list = list,
        expanded = expanded,
        selectState = onSelect,
        selectedState = selectedState,
        title = title,
        onDismissRequest = { expanded = false })


}

@Composable
fun RenderDropDownModal(
    list: List<String>,
    expanded: Boolean,
    selectState: (String) -> Unit,
    selectedState: String?,
    title: String,
    onDismissRequest: () -> Unit
) {
    if (expanded) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = onDismissRequest,
            confirmButton = { /*TODO*/ },
            title = {
                Text(
                    text = title
                )
            },
            text = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(list) { item ->
                        DropDownItem(item = item, isSelected = item == selectedState, onSelect = {
                            selectState(it)
                            onDismissRequest()
                        })
                        HorizontalDivider()
                    }
                }
            }
        )
    }
}

@Composable
fun DropDownItem(
    item: String,
    isSelected: Boolean = false,
    onSelect: (String) -> Unit
) {
    DropdownMenuItem(
        colors = MenuDefaults.itemColors(textColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black),
        text = { Text(item) },
        onClick = { onSelect(item) })
}

@Composable
@Preview
fun CustomDropdownPreview() {
    RelicRegistryTheme {
        CustomDropdown(list = listOf(), selectedState = null, onSelect = {}, title = "teste")
    }
}

