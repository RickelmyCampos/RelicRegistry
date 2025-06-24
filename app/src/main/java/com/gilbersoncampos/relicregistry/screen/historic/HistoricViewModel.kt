package com.gilbersoncampos.relicregistry.screen.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.repository.HistoricSyncRepository
import com.gilbersoncampos.relicregistry.data.useCase.DeleteCacheUseCase
import com.gilbersoncampos.relicregistry.screen.recordList.RecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricViewModel @Inject constructor(private val historicSyncRepository: HistoricSyncRepository) :
    ViewModel() {
    private var _uiState = MutableStateFlow<HistoricUiState>(HistoricUiState.Loading)
    val uiState: StateFlow<HistoricUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            historicSyncRepository.getAllHistoricSync().collect { list ->
                _uiState.value = HistoricUiState.Success(list)
            }
        }
    }

}

sealed class HistoricUiState {
    data class Success(
        val historic: List<HistoricSyncModel>,

    ) : HistoricUiState()

    data object Error : HistoricUiState()
    data object Loading : HistoricUiState()

}

