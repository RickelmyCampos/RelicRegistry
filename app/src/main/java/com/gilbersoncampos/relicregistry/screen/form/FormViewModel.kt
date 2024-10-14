package com.gilbersoncampos.relicregistry.screen.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.Constants.formDefault
import com.gilbersoncampos.relicregistry.data.model.Form
import com.gilbersoncampos.relicregistry.data.model.FormField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//data class FormUiState(
//    val form:Form,
//    val onUpdateForm:(Form)->Unit
//)
sealed interface FormUiState {
    data class Success(val form: Form, val onUpdateField: (FormField) -> Unit) : FormUiState
    data object Empty : FormUiState
}

@HiltViewModel
class FormViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<FormUiState>(FormUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
//        viewModelScope.launch {
//            delay(2_000)
            _uiState.value = FormUiState.Success(
                form = formDefault,
                onUpdateField = { updatedField ->
                    _uiState.update { currentState ->
                        if (currentState is FormUiState.Success) {
                            val updatedFields = currentState.form.fields.map { field ->
                                if (field.id == updatedField.id) updatedField else field
                            }
                            currentState.copy(form = currentState.form.copy(fields = updatedFields))
                        } else currentState
                    }
                }
            )

        }


}