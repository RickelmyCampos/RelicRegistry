package com.gilbersoncampos.relicregistry.data.repository

import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import kotlinx.coroutines.flow.Flow

interface RecordRepository{
    suspend fun createRecord(record: CatalogRecordModel)
    suspend fun getAllRecord(): Flow<List<CatalogRecordModel>>
    suspend fun getLastRecord():CatalogRecordModel
    suspend fun getRecordById(id:Long): Flow<CatalogRecordModel>
    suspend fun updateRecord(record: CatalogRecordModel)
    suspend fun removeRecords(recordList:List<CatalogRecordModel>)
    suspend fun getAllArchaeologicalSite():Flow<List<String>>
    suspend fun syncRecords()
}