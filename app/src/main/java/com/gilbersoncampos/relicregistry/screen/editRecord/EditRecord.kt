package com.gilbersoncampos.relicregistry.screen.editRecord

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.Constants.antiplastic
import com.gilbersoncampos.relicregistry.Constants.bodyPosition
import com.gilbersoncampos.relicregistry.Constants.burn
import com.gilbersoncampos.relicregistry.Constants.decorationType
import com.gilbersoncampos.relicregistry.Constants.fabricationMarks
import com.gilbersoncampos.relicregistry.Constants.fabricationTechnique
import com.gilbersoncampos.relicregistry.Constants.formCondition
import com.gilbersoncampos.relicregistry.Constants.formGeneralBodyShape
import com.gilbersoncampos.relicregistry.Constants.formTypes
import com.gilbersoncampos.relicregistry.Constants.location
import com.gilbersoncampos.relicregistry.Constants.lowerLimbs
import com.gilbersoncampos.relicregistry.Constants.otherFormalAttributes
import com.gilbersoncampos.relicregistry.Constants.paintColorFI
import com.gilbersoncampos.relicregistry.Constants.plasticDecoration
import com.gilbersoncampos.relicregistry.Constants.surfaceTreatment
import com.gilbersoncampos.relicregistry.Constants.surfaceTreatmentET
import com.gilbersoncampos.relicregistry.Constants.upperLimbs
import com.gilbersoncampos.relicregistry.Constants.usageMarks
import com.gilbersoncampos.relicregistry.Constants.uses
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.extensions.toOnlyFloat
import com.gilbersoncampos.relicregistry.ui.components.CustomDropdown
import com.gilbersoncampos.relicregistry.ui.components.ListCheckbox
import com.gilbersoncampos.relicregistry.ui.components.Session
import com.gilbersoncampos.relicregistry.ui.components.SubSession
import com.gilbersoncampos.relicregistry.ui.components.TextCheckbox
import com.gilbersoncampos.relicregistry.ui.components.TextRadioButton
import kotlin.reflect.KFunction1

@Composable
fun EditRecord(idRecord: Int, viewModel: EditRecordViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(idRecord) {
        viewModel.getRecord(idRecord)
    }
    EditRecordUi(
        uiState = uiState,
        updateRecord = viewModel::updateRecord,
        saveRecord = viewModel::saveRecord
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecordUi(
    uiState: EditRecordUiState,
    updateRecord: (RecordModel) -> Unit,
    saveRecord: () -> Unit
) {


    when (uiState) {
        is EditRecordUiState.Error -> Text(text = uiState.message)
        EditRecordUiState.Loading -> Text(text = "Carregando")
        is EditRecordUiState.Success -> Column {
            TopAppBar(
                title = { Text(text = "Ficha: ${uiState.state.record.numbering}") },
                actions = {
                    Row {
                        if (!uiState.state.isSynchronized) {
                            IconButton(onClick = { saveRecord() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_save),
                                    contentDescription = "save_record"
                                )
                            }
                        }

                    }
                })
            EditRecordForm(uiState.state, updateRecord)
        }

    }
}


@Composable
fun EditRecordForm(uiState: SuccessUiState, updateRecord: (RecordModel) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
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
        Session(title = "Dados da Ficha") {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                InfosRow(title = "Numeração:", value = uiState.record.numbering)
                InfosRow(title = "Sítio arqueológico:", value = uiState.record.place)
                InfosRow(title = "Prateleira:", value = uiState.record.shelf)
                InfosRow(title = "Grupo:", value = uiState.record.group)
            }
        }
        Session(title = "Dimensões") {
            Column {
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        label = { Text(text = "Comprimento (cm)") },
                        value = uiState.record.length.toString(),
                        onValueChange = { updateRecord(uiState.record.copy(length = it.toOnlyFloat())) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        label = { Text(text = "Largura (cm)") },
                        value = uiState.record.width.toString(),
                        onValueChange = { updateRecord(uiState.record.copy(width = it.toOnlyFloat())) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        label = { Text(text = "Altura (cm)") },
                        value = uiState.record.height.toString(),
                        onValueChange = { updateRecord(uiState.record.copy(height = it.toOnlyFloat())) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        label = { Text(text = "Peso (kg)") },
                        value = uiState.record.weight.toString(),
                        onValueChange = { updateRecord(uiState.record.copy(weight = it.toOnlyFloat())) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
        Session(title = "Tipologia") {
            SubSession(title = "Tipo") {
                CustomDropdown(
                    title = "Tipo",
                    list = formTypes,
                    selectedState = uiState.record.formType,
                    onSelect = { updateRecord(uiState.record.copy(formType = it)) })
            }
            SubSession(title = "Condição") {
                CustomDropdown(
                    title = "Condição",
                    list = formCondition,
                    selectedState = uiState.record.formCondition,
                    onSelect = { updateRecord(uiState.record.copy(formCondition = it)) })
            }
            SubSession(title = "Forma geral do Corpo") {

                CustomDropdown(
                    title = "Forma geral do Corpo",
                    list = formGeneralBodyShape,
                    selectedState = uiState.record.formGeneralBodyShape,
                    onSelect = { updateRecord(uiState.record.copy(formGeneralBodyShape = it)) })
            }
        }
        Session(title = "Morfologia") {
            SubSession(title = "Membros superiores") {
                ListCheckbox(
                    list = upperLimbs,
                    uiState.record.upperLimbs
                ) { selected, _ -> updateRecord(uiState.record.copy(upperLimbs = selected)) }
            }
            SubSession(title = "Membros inferiores") {
                ListCheckbox(
                    list = lowerLimbs,
                    uiState.record.lowerLimbs
                ) { selected, _ -> updateRecord(uiState.record.copy(lowerLimbs = selected)) }
            }
            SubSession(title = "Fabricação de genitália") {
                var hasGenitalia by remember {
                    mutableStateOf(false)
                }
                TextCheckbox(
                    onCheckedChange = { checked ->
                        hasGenitalia = checked
                        if (!checked) {
                            updateRecord(uiState.record.copy(genitalia = null))
                        }
                    },
                    label = "Possui genitália",
                    hasGenitalia
                )
                if (hasGenitalia) {

                    TextRadioButton(
                        listOf("Masculina", "Feminina"),
                        uiState.record.genitalia
                    ) { updateRecord(uiState.record.copy(genitalia = it)) }
                }
            }
            SubSession(title = "Posição corporal") {
                ListCheckbox(list = bodyPosition, uiState.record.bodyPosition) { selected, _ ->
                    updateRecord(uiState.record.copy(bodyPosition = selected))
                }
            }
            SubSession(title = "Outros atributos formais") {
                ListCheckbox(
                    list = otherFormalAttributes,
                    uiState.record.otherFormalAttributes
                ) { selected, _ ->
                    updateRecord(uiState.record.copy(otherFormalAttributes = selected))

                }
            }
        }
        Session(title = "Tecnologia") {
            SubSession(title = "Queima") {
                ListCheckbox(list = burn, uiState.record.burn) { selected, _ ->
                    updateRecord(uiState.record.copy(burn = selected))

                }
            }
            SubSession(title = "Antiplástico") {
                ListCheckbox(list = antiplastic, uiState.record.antiplastic) { selected, _ ->
                    updateRecord(uiState.record.copy(antiplastic = selected))

                }
            }
            SubSession(title = "Técnica de fabricação") {
                ListCheckbox(
                    list = fabricationTechnique,
                    uiState.record.fabricationTechnique
                ) { selected, _ ->
                    updateRecord(uiState.record.copy(fabricationTechnique = selected))

                }
            }
            SubSession(title = "Marcas de fabricação") {
                ListCheckbox(
                    list = fabricationMarks,
                    uiState.record.fabricationMarks
                ) { selected, _ ->
                    updateRecord(uiState.record.copy(fabricationMarks = selected))

                }
            }
            SubSession(title = "Marcas de uso") {
                ListCheckbox(list = usageMarks, uiState.record.usageMarks) { selected, _ ->
                    updateRecord(uiState.record.copy(usageMarks = selected))

                }
            }
            SubSession(title = "Tratamento de SUP. (I.T.)") {
                ListCheckbox(
                    list = surfaceTreatment,
                    uiState.record.surfaceTreatment
                ) { selected, _ ->
                    updateRecord(uiState.record.copy(surfaceTreatment = selected))

                }
            }
            SubSession(title = "Tratamento de SUP. (E.T.)") {
                ListCheckbox(
                    list = surfaceTreatmentET,
                    uiState.record.surfaceTreatmentET
                ) { selected, _ ->
                    updateRecord(uiState.record.copy(surfaceTreatmentET = selected))

                }
            }
        }
        Session(title = "Decoraçao") {
            TextCheckbox(
                onCheckedChange = { hasSelected -> updateRecord(uiState.record.copy(decoration = hasSelected)) },
                label = "Possui decoraçao",
                uiState.record.decoration
            )
            if (uiState.record.decoration) {
                SubSession(title = "Local") {
                    TextRadioButton(
                        location,
                        uiState.record.location
                    ) { updateRecord(uiState.record.copy(location = it)) }
                }
                SubSession(title = "Tipo de Decoraçao") {
                    ListCheckbox(
                        list = decorationType,
                        uiState.record.decorationType
                    ) { selected, _ ->
                        updateRecord(uiState.record.copy(decorationType = selected))

                    }
                }
                SubSession(title = "Cor da pintura (F.I.)") {
                    ListCheckbox(list = paintColorFI, uiState.record.paintColorFI) { selected, _ ->
                        updateRecord(uiState.record.copy(paintColorFI = selected))

                    }
                }
                SubSession(title = "Cor da pintura (F.E.)") {
                    ListCheckbox(list = paintColorFI, uiState.record.paintColorFE) { selected, _ ->
                        updateRecord(uiState.record.copy(paintColorFE = selected))

                    }
                }
                SubSession(title = "Decoração plástica") {
                    ListCheckbox(
                        list = plasticDecoration,
                        uiState.record.plasticDecoration
                    ) { selected, _ ->
                        updateRecord(uiState.record.copy(plasticDecoration = selected))

                    }
                }
            }
        }
        Session(title = "Usos") {
            ListCheckbox(list = uses, uiState.record.uses) { selected, _ ->
                updateRecord(uiState.record.copy(uses = selected))

            }
        }
    }
}

@Composable
private fun InfosRow(title: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
@Preview(showBackground = true)
fun EditRecordPreview() {
    EditRecordUi(
        uiState = EditRecordUiState.Loading,
        updateRecord = { },
        saveRecord = {}
    )
}

@Composable
@Preview(showBackground = true)
fun EditRecordFormPreview() {
    MaterialTheme {
        EditRecordForm(uiState = object : SuccessUiState {
            override val record: RecordModel
                get() = RecordModel(0, "8", "caboclo", "12", "2", "2")
            override val isSynchronized: Boolean
                get() = false

        }, {})
    }
}