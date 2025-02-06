package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import com.gilbersoncampos.relicregistry.extensions.getNameTranslated
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme


@Composable
fun <T> CustomDropdown(
    list: List<T>,
    selectedState: T?,
    onSelect: (T) -> Unit,
    title: String,
    hasSearch: Boolean = false
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
        Text(
            text = when (selectedState) {
                is Enum<*> -> selectedState.getNameTranslated()
                null -> "Selecione uma opção"
                else -> selectedState.toString()
            }
        )
    }
    RenderDropDownModal(
        list = list,
        expanded = expanded,
        selectState = onSelect,
        selectedState = selectedState,
        title = title,
        onDismissRequest = { expanded = false },
        hasSearch = hasSearch
    )


}

@Composable
fun <T> RenderDropDownModal(
    list: List<T>,
    expanded: Boolean,
    selectState: (T) -> Unit,
    selectedState: T?,
    title: String,
    onDismissRequest: () -> Unit,
    hasSearch: Boolean
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
                var searchWords by remember { mutableStateOf("") }
                val listFiltered = list.filter {
                    if (searchWords.isBlank()) return@filter true
                    val text = when (it) {
                        is Enum<*> -> it.getNameTranslated()
                        else -> it.toString()
                    }
                    text.contains(searchWords,true)
                }
                Column {
                    if (hasSearch) {
                        OutlinedTextField(
                            value = searchWords,
                            onValueChange = { searchWords = it },
                            label = { Text("Pesquisar") }
                        )
                    }
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(listFiltered) { item ->
                            DropDownItem(
                                item = item,
                                isSelected = item == selectedState,
                                onSelect = {
                                    selectState(it)
                                    onDismissRequest()
                                })
                            HorizontalDivider()
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun <T> DropDownItem(
    item: T,
    isSelected: Boolean = false,
    onSelect: (T) -> Unit
) {
    DropdownMenuItem(
        colors = MenuDefaults.itemColors(textColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black),
        text = {
            Text(
                when (item) {
                    is Enum<*> -> item.getNameTranslated()
                    null-> "Selecione uma opção"
                    else -> item.toString()
                }
            )
        },
        onClick = { onSelect(item) })
}

@Composable
@Preview
fun CustomDropdownPreview() {
    RelicRegistryTheme {
        CustomDropdown(
            list = listOf("TESTE", "ALgo"),
            selectedState = null,
            onSelect = {},
            title = "teste",
            hasSearch = true
        )
    }
}

