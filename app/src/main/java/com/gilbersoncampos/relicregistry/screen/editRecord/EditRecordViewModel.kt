package com.gilbersoncampos.relicregistry.screen.editRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EditRecordViewModel @Inject constructor(private val repository: RecordRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<EditRecordUiState>(EditRecordUiState.Loading)
    val uiState: StateFlow<EditRecordUiState> = _uiState
    private lateinit var _savedRecord: RecordModel
    fun getRecord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordById(id).collectLatest {
                _savedRecord = it
                _uiState.value =
                    EditRecordUiState.Success(MutableSuccessUiState(_savedRecord.copy(), true))
            }
        }
    }

    fun updateRecord(record: RecordModel) {
        _uiState.value = EditRecordUiState.Success(MutableSuccessUiState(record))
        verifySynchronized()
    }

    private fun verifySynchronized() {
        when (_uiState.value) {
            is EditRecordUiState.Error -> TODO()
            EditRecordUiState.Loading -> TODO()
            is EditRecordUiState.Success -> _uiState.value = EditRecordUiState.Success(
                MutableSuccessUiState(
                    (_uiState.value as EditRecordUiState.Success).state.record.copy(),
                    _savedRecord == (_uiState.value as EditRecordUiState.Success).state.record
                )
            )
        }

    }

    fun saveRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecord((_uiState.value as EditRecordUiState.Success).state.record)
        }
    }
}

sealed class EditRecordUiState {
    data object Loading : EditRecordUiState()
    data class Success(val state: SuccessUiState) : EditRecordUiState()
    data class Error(val message: String) : EditRecordUiState()
}

class MutableSuccessUiState(
    override val record: RecordModel,
    override val isSynchronized: Boolean = false
) : SuccessUiState

interface SuccessUiState {
    val record: RecordModel
    val isSynchronized: Boolean
}