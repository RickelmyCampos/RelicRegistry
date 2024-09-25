package com.gilbersoncampos.relicregistry.screen.editRecord

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import com.gilbersoncampos.relicregistry.data.services.PdfService
import com.gilbersoncampos.relicregistry.service.ExternalPdfService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EditRecordViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val imageStoreService: ImageStoreService,
    private val pdfService: PdfService
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<EditRecordUiState>(EditRecordUiState.Loading)
    val uiState: StateFlow<EditRecordUiState> = _uiState
    private lateinit var _savedRecord: CatalogRecordModel

    fun getRecord(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordById(id).collectLatest { record ->
                _savedRecord = record
                delay(300)
                updateUiState( _savedRecord.copy())
                viewModelScope.launch(Dispatchers.IO) {
                    val bitmaps = _savedRecord.listImages.map { getImage(it) }
                    updateUiState(_savedRecord.copy(), images = bitmaps)
                }

            }
        }
    }
    fun saveImages(uris: List<Uri>) {
        val imageNames: MutableList<String> = mutableListOf()
        val imagesBitmaps: MutableList<Bitmap> = mutableListOf()
        deleteOldImages()
        uris.forEachIndexed { index, item ->
            val name =
                "Record_${(_uiState.value as EditRecordUiState.Success).state.record.identification}_$index"
            imageStoreService.saveImageByUri(
                item,
                name
            )
            imageNames.add(name)
            imagesBitmaps.add(getImage(name))
            updateUiState(record = (_uiState.value as EditRecordUiState.Success).state.record.copy(listImages = imageNames), images = imagesBitmaps)
        }
    }

    private fun deleteOldImages() {
        (_uiState.value as EditRecordUiState.Success).state.record.listImages.forEach {
            imageStoreService.deleteImageByNameImage(it)
        }
    }

    fun getImage(name: String): Bitmap {
        return imageStoreService.getImage(nameImage = name)
    }
    fun saveRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecord((_uiState.value as EditRecordUiState.Success).state.record)
        }
    }
    fun updateUiState(record: CatalogRecordModel, images: List<Bitmap> = emptyList()) {
        _uiState.value = EditRecordUiState.Success(
            SuccessUiState(
                record = record,
                isSynchronized = _savedRecord == record,
                images = images.ifEmpty { (_uiState.value as? EditRecordUiState.Success)?.state?.images.orEmpty() }
            )
        )
    }

    fun generatePdf(){
        pdfService.generatePdf(_savedRecord, listImages =(_uiState.value as? EditRecordUiState.Success)?.state?.images?: listOf())
    }
    fun getPDF():File{
       return pdfService.getPDF()
    }
}

sealed class EditRecordUiState {
    data object Loading : EditRecordUiState()
    data class Success(val state: SuccessUiState) : EditRecordUiState()
    data class Error(val message: String) : EditRecordUiState()
}


data class SuccessUiState(
    val record: CatalogRecordModel,
    val isSynchronized: Boolean,
    val images: List<Bitmap>
)
