package com.gilbersoncampos.relicregistry

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RecordRepository) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createRecord(record: CatalogRecordModel, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            repository.createRecord(record.copy(createdAt = LocalDateTime.now()))
            val id = getLastRecordId()
            Log.d(javaClass.simpleName, "createRecord: $id")
            onSaved(id)
        }
    }

    private suspend fun getLastRecordId(): Long {
        val records = repository.getLastRecord()
        return records.id

    }

}