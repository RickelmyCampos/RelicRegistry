package com.gilbersoncampos.relicregistry

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import com.gilbersoncampos.relicregistry.screen.editRecord.EditRecordUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RecordRepository) : ViewModel() {
    private var _textLoadingState= MutableStateFlow<String?>(null)
     val textLoadingState: StateFlow<String?> = _textLoadingState.asStateFlow()
    @RequiresApi(Build.VERSION_CODES.O)
    fun createRecord(record: CatalogRecordModel, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            repository.createRecord(record.copy(createdAt = LocalDateTime.now()))
            val id = getLastRecordId()
            Log.d(javaClass.simpleName, "createRecord: $id")
            onSaved(id)
        }
    }
    fun setTextLoading(text:String?){
        _textLoadingState.value = text
    }
    fun checkForUpdate(currentVersionCode: Int,onCallback: (Boolean,VersionInfo?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _textLoadingState.value = "Verificando atualizações..."
            try {
                val url = URL(VERSION_INFO_URL)
                val jsonString = url.readText()
                val versionInfo = Gson().fromJson(jsonString, VersionInfo::class.java)
                if (versionInfo.latestVersionCode > currentVersionCode) {
//                        _versionInfo = versionInfo
//                        _showUpdateDialog.value = true
                    onCallback(true,versionInfo)
                    Log.w(
                        TAG,
                        "ATUALIZAÇÃO DISPONÍVEL :${versionInfo.latestVersionCode} - ${currentVersionCode}"
                    )
                } else {
                    onCallback(false,null)
                    Log.w(TAG, "Já Atualizado")
                }

            } catch (e: IOException) {
                onCallback(true,null)
                // _statusText.value = "Erro ao verificar atualizações: ${e.message}"
                Log.e(TAG, "Erro ao verificar atualizações: ${e.message}", e)

            }
            _textLoadingState.value = null
        }

    }

    private suspend fun getLastRecordId(): Long {
        val records = repository.getLastRecord()
        return records.id

    }

    companion object {
        private val TAG = "MainViewModel"
        private val VERSION_INFO_URL =
            "https://drive.google.com/uc?export=download&id=1AwVpXyXLQAJ58Ze8Gj2fpQLLI-f6Gr65"// Substitua pelo seu URL real

    }
}

data class VersionInfo(
    val latestVersionCode: Int,
    val latestVersionName: String,
    val apkUrl: String,
    val releaseNotes: String
)