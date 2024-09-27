package com.gilbersoncampos.relicregistry.screen.recordList

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(private val repository: RecordRepository,private val imageStoreService: ImageStoreService) :
    ViewModel() {
    private var _uiState = MutableStateFlow<RecordUiState>(RecordUiState.Loading)
    val uiState: StateFlow<RecordUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getAllRecord().collect { list ->
                _uiState.value = RecordUiState.Success(list)
            }

        }

    }
    fun getImage(name:String):Bitmap{

        return imageStoreService.getImage(name,false)
    }
}

sealed class RecordUiState {
    data class Success(val records: List<CatalogRecordModel>) : RecordUiState()
    data object Error : RecordUiState()
    data object Loading : RecordUiState()

}