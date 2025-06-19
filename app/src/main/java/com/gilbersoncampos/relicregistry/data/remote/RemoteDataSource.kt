package com.gilbersoncampos.relicregistry.data.remote

import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getAllRecord(): Flow<List<CatalogRecordModel>>
    fun getRecordById(id: Long): Flow<CatalogRecordModel>
    suspend fun createRecord(record: CatalogRecordModel)
    suspend fun updateRecord(record: CatalogRecordModel)
}