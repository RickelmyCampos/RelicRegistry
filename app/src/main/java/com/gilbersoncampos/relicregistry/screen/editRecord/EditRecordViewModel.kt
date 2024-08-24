package com.gilbersoncampos.relicregistry.screen.editRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EditRecordViewModel @Inject constructor(private val repository: RecordRepository):ViewModel() {
    private val _uiState = MutableStateFlow<EditRecordUiState>(EditRecordUiState.Loading)
    val uiState: StateFlow<EditRecordUiState> = _uiState
    fun getRecord(id:Int) {
        viewModelScope.launch {
            _uiState.value=EditRecordUiState.Success(repository.getRecordById(id))
        }
    }
}
sealed class EditRecordUiState {
    data object Loading:EditRecordUiState()
    data class Success(val record: RecordModel):EditRecordUiState()
    data class Error(val message:String):EditRecordUiState()

}