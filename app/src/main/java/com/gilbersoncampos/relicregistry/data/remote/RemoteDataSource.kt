package com.gilbersoncampos.relicregistry.data.remote

import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getAllRecord(): Flow<List<CatalogRecordModel>>
    suspend fun getRecordByIdRemote(id: String): CatalogRecordModel?
    suspend fun createRecord(record: CatalogRecordModel): String
    suspend fun updateRecord(record: CatalogRecordModel)
}