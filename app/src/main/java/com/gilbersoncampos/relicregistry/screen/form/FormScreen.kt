package com.gilbersoncampos.relicregistry.screen.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.Constants.formDefault
import com.gilbersoncampos.relicregistry.data.enums.FormFieldType
import com.gilbersoncampos.relicregistry.data.model.Form
import com.gilbersoncampos.relicregistry.data.model.FormField
import com.gilbersoncampos.relicregistry.navigation.Destination
import com.gilbersoncampos.relicregistry.ui.components.FormFieldMultipleSelectOptions
import com.gilbersoncampos.relicregistry.ui.components.FormFieldSelectOptions
import com.gilbersoncampos.relicregistry.ui.components.FormFieldText

@Composable
fun FormScreen(viewModel: FormViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        FormUiState.Empty -> {
            Text(text = "Form Vazio")
        }

        is FormUiState.Success -> {
            val form = (uiState as FormUiState.Success).form
            val onUpdateForm = (uiState as FormUiState.Success).onUpdateField
            FormUi(form, onUpdateForm)
        }
    }


}

@Composable
fun FormUi(form: Form, onUpdateFormField: (FormField) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = form.title)
        LazyColumn() {
            items(form.fields, key = { it.id }) {
                when (it.typeField) {
                    FormFieldType.TEXT -> {
                        FormFieldText(it) { formField ->
                            onUpdateFormField(formField)
                        }
                    }

                    FormFieldType.UNIQUEOPTION -> {
                        FormFieldSelectOptions(it) { formField ->
                            onUpdateFormField(formField)
                        }
                    }

                    FormFieldType.NUMBER -> {}
                    FormFieldType.LIST -> {}
                    FormFieldType.BOOLEAN -> {}
                    FormFieldType.MULTIPLEOPTIONS -> {
                        FormFieldMultipleSelectOptions(formFieldText = it) { formField ->
                            onUpdateFormField(formField)
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun FormUiPreview() {
    FormUi(formDefault) {}
}