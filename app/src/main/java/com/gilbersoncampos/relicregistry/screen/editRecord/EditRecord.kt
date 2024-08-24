package com.gilbersoncampos.relicregistry.screen.editRecord

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.Constants.formTypes
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.DropdownData
import com.gilbersoncampos.relicregistry.data.model.RecordModel

@Composable
fun EditRecord(idRecord: Int, viewModel: EditRecordViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(idRecord) {
        viewModel.getRecord(idRecord)
    }
    EditRecordUi(uiState = uiState)
}

@Composable
fun EditRecordUi(uiState: EditRecordUiState) {
    Column(Modifier.fillMaxSize()) {
        when (uiState) {
            is EditRecordUiState.Error -> Text(text = uiState.message)
            EditRecordUiState.Loading -> Text(text = "Carregando")
            is EditRecordUiState.Success -> EditRecordForm(uiState.record)
        }
    }
}

@Composable
fun EditRecordForm(record: RecordModel) {
    Column(Modifier.fillMaxSize()) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_image_basic),
                contentDescription = "Image",
                modifier = Modifier.size(200.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_image_basic),
                contentDescription = "Image",
                modifier = Modifier.size(200.dp)
            )
        }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(text = "Comprimento") },
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                label = { Text(text = "Largura") },
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(text = "Altura") },
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                label = { Text(text = "Peso") },
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
        }
        var type by remember {
            mutableStateOf<DropdownData?>(null)
        }
        ComponentDropDown(
            title = "Tipo",
            list = formTypes,
            selectedState = type,
            onSelect = { type = it })
    }
}

@Composable
fun ComponentDropDown(
    title: String,
    list: List<DropdownData>,
    selectedState: DropdownData?,
    onSelect: (DropdownData) -> Unit
) {
    Text(text = title)
    DropdownSelect(
        list = list,
        selectedState = selectedState,
        onSelect = onSelect,
        title = title
    )
}

@Composable
fun DropdownSelect(
    list: List<DropdownData>,
    selectedState: DropdownData?,
    onSelect: (DropdownData) -> Unit,
    title: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Button(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors().copy()
    ) {
        Text(text = selectedState?.name ?: "Selecione uma opção")
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
    list: List<DropdownData>,
    expanded: Boolean,
    selectState: (DropdownData) -> Unit,
    selectedState: DropdownData?,
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
    item: DropdownData,
    isSelected: Boolean = false,
    onSelect: (DropdownData) -> Unit
) {
    DropdownMenuItem(
        colors = MenuDefaults.itemColors(textColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black),
        text = { Text(item.name) },
        onClick = { onSelect(item) })
}


@Composable
@Preview(showBackground = true)
fun EditRecordPreview() {
    EditRecordUi(
        uiState = EditRecordUiState.Success(
            RecordModel(
                0,
                "8",
                "caboclo",
                "12",
                "2",
                "2"
            )
        )
    )
}

@Composable
@Preview(showBackground = true)
fun EditRecordFormPreview() {
    MaterialTheme {
        EditRecordForm(RecordModel(0, "8", "caboclo", "12", "2", "2"))
    }
}