package com.gilbersoncampos.relicregistry.screen.editRecord

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EditRecordViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val imageStoreService: ImageStoreService
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<EditRecordUiState>(EditRecordUiState.Loading)
    val uiState: StateFlow<EditRecordUiState> = _uiState
    private lateinit var _savedRecord: RecordModel
    fun getRecord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordById(id).collectLatest { record ->
                _savedRecord = record
                val imageNames = _savedRecord.listImages
                _uiState.value = EditRecordUiState.Success(
                    MutableSuccessUiState(
                        _savedRecord.copy(),
                        true,
                        emptyList()
                    )
                )
                viewModelScope.launch(Dispatchers.IO) {
                    val bitmaps = _savedRecord.listImages.map { getImage(it) }
                    _uiState.value = EditRecordUiState.Success(
                        MutableSuccessUiState(
                            _savedRecord.copy(),
                            true,
                            bitmaps
                        )
                    )
                }

            }
        }
    }


    fun updateRecord(record: RecordModel) {
        _uiState.value = EditRecordUiState.Success(
            MutableSuccessUiState(
                record,
                images = (_uiState.value as EditRecordUiState.Success).state.images
            )
        )
        verifySynchronized()
    }

    private fun verifySynchronized() {
        when (_uiState.value) {
            is EditRecordUiState.Error -> TODO()
            EditRecordUiState.Loading -> TODO()
            is EditRecordUiState.Success -> _uiState.value = EditRecordUiState.Success(
                MutableSuccessUiState(
                    record = (_uiState.value as EditRecordUiState.Success).state.record.copy(),
                    isSynchronized = _savedRecord == (_uiState.value as EditRecordUiState.Success).state.record,
                    images =  (_uiState.value as EditRecordUiState.Success).state.images
                )
            )
        }

    }

    fun saveImages(uris: List<Uri>) {
        val imageNames: MutableList<String> = mutableListOf()
        val imagesBitmaps: MutableList<Bitmap> = mutableListOf()
        uris.forEachIndexed { index, item ->
            val name =
                "Record_${(_uiState.value as EditRecordUiState.Success).state.record.numbering}_$index"
            imageStoreService.saveImageByUri(
                item,
                name
            )
            imageNames.add(name)
            imagesBitmaps.add(getImage(name))
        }

        _uiState.value = EditRecordUiState.Success(
            MutableSuccessUiState(
                record = (_uiState.value as EditRecordUiState.Success).state.record.copy(listImages = imageNames),
                isSynchronized = _savedRecord == (_uiState.value as EditRecordUiState.Success).state.record,
                images =  imagesBitmaps
            )
        )
        verifySynchronized()
    }

    fun getImage(name: String): Bitmap {
        return imageStoreService.getImage(nameImage = name)
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
    override val isSynchronized: Boolean = false, override val images: List<Bitmap>
) : SuccessUiState

interface SuccessUiState {
    val record: RecordModel
    val isSynchronized: Boolean
    val images: List<Bitmap>
}