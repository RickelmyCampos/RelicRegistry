package com.gilbersoncampos.relicregistry.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gilbersoncampos.relicregistry.data.enums.FormFieldType
import com.gilbersoncampos.relicregistry.data.model.FormField
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun FormFieldText(formFieldText: FormField, onUpdateFormField: (FormField) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = formFieldText.label, style = MaterialTheme.typography.titleLarge)
        TextField(modifier = Modifier.fillMaxWidth(),value = formFieldText.value, onValueChange = {
            val formField = formFieldText.copy(value = it)
            onUpdateFormField(formField)
        })
    }
}

@Composable
fun FormFieldSelectOptions(formFieldText: FormField, onUpdateFormField: (FormField) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = formFieldText.label, style = MaterialTheme.typography.titleLarge)
        val options: List<String> = formFieldText.options?.split("|") ?: listOf()


        options.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (formFieldText.value == it),
                    onClick = {
                        val formField = formFieldText.copy(value = it)
                        onUpdateFormField(formField)
                    }
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

}

@Composable
fun FormFieldMultipleSelectOptions(
    formFieldText: FormField,
    onUpdateFormField: (FormField) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = formFieldText.label, style = MaterialTheme.typography.titleLarge)
        val options: List<String> = formFieldText.options?.split("|") ?: listOf()
        val valuesSelected: List<String> = formFieldText.value.split("|")

        options.forEach {
            val formField = formFieldText.copy(value = onClickOptionList(valuesSelected, it))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onUpdateFormField(formField)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = it in valuesSelected, onCheckedChange = {
                    onUpdateFormField(formField)
                })
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

fun onClickOptionList(values: List<String>, selected: String): String {
    val listAux = values.toMutableList()

    val isSelected = selected in listAux
    if (isSelected) {
        listAux.remove(selected)
    } else {
        listAux.add(selected)
    }
    return listAux.joinToString("|")
}

@Composable
@Preview(showBackground = true)
fun FormFieldTextPreview() {
    val formOption = FormField("", "Título do campo", FormFieldType.TEXT, "")
    RelicRegistryTheme {

        FormFieldText(formOption) {}
    }
}

@Composable
@Preview(showBackground = true)
fun FormFieldMultipleSelectOptionsPreview() {
    val formOption = FormField(
        "",
        "Título do campo",
        FormFieldType.MULTIPLEOPTIONS,
        "",
        options = "opção1|opção2|opção3"
    )
    RelicRegistryTheme {

        FormFieldMultipleSelectOptions(formOption) {}
    }
}

@Composable
@Preview(showBackground = true)
fun FormFieldSelectOptionsPreview() {
    val formOption = FormField(
        "",
        "Título do campo",
        FormFieldType.MULTIPLEOPTIONS,
        "opção1|opção2",
        options = "opção1|opção2|opção3"
    )
    RelicRegistryTheme {

        FormFieldSelectOptions(formOption) {}
    }
}