package com.gilbersoncampos.relicregistry.screen.recordList

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.repository.HistoricSyncRepository
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import com.gilbersoncampos.relicregistry.worker.HistoricListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val imageStoreService: ImageStoreService,
    private val historicRepository: HistoricSyncRepository
) :
    ViewModel() {
    private var _uiState = MutableStateFlow<RecordUiState>(RecordUiState.Loading)
    val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()
    private val _listRecord = mutableStateListOf<CatalogRecordModel>()

    init {
        viewModelScope.launch {
            repository.getAllRecord().collect { list ->
                _uiState.value = RecordUiState.Success(list)
            }
        }

    }

    fun selectRecords(record: CatalogRecordModel) {
        if (_listRecord.contains(record)) {
            _listRecord.remove(record)
        } else {
            _listRecord.add(record)
        }
        _uiState.update { old ->
            when (old) {
                is RecordUiState.Success -> {
                    old.copy(recordsSelected = _listRecord.toList())
                }

                else -> old
            }
        }

    }
    fun syncRecords(){
        val listener= object : HistoricListener {
            override suspend fun updateHistoric(historicSyncModel: HistoricSyncModel) {
                historicRepository.updateHistoricSync(historicSyncModel)
            }
        }
        viewModelScope.launch {
            repository.syncRecords(listener)
        }
    }

    fun removeRecordsSelected() {
        viewModelScope.launch {
            repository.removeRecords(_listRecord)
        }
    }

    fun getImage(name: String): File {
        return imageStoreService.getImageFile(name, false)
    }

}

sealed class RecordUiState {
    data class Success(
        val records: List<CatalogRecordModel>,
        val recordsSelected: List<CatalogRecordModel> = emptyList()
    ) : RecordUiState()

    data object Error : RecordUiState()
    data object Loading : RecordUiState()

}