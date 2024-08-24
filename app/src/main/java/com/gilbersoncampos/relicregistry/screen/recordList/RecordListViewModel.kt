package com.gilbersoncampos.relicregistry.screen.recordList

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
class RecordListViewModel @Inject constructor(private val repository: RecordRepository) :
    ViewModel() {
    private var _uiState = MutableStateFlow<RecordUiState>(RecordUiState.Loading)
    val uiState: StateFlow<RecordUiState> = _uiState

    init {
        viewModelScope.launch {
            val list = repository.getAllRecord()
            _uiState.value = RecordUiState.Success(list)
        }

    }
}

sealed class RecordUiState {
    data class Success(val records: List<RecordModel>) : RecordUiState()
    object Error : RecordUiState()
    object Loading : RecordUiState()

}