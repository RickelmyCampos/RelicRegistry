package com.gilbersoncampos.relicregistry.data.repository

import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import kotlinx.coroutines.flow.Flow

interface HistoricSyncRepository {
    suspend fun createHistoricSync(historic: HistoricSyncModel)
    suspend fun getAllHistoricSync(): Flow<List<HistoricSyncModel>>
    suspend fun updateHistoricSync(historic: HistoricSyncModel)
    suspend fun getLastHistoricSync():HistoricSyncModel
    suspend fun getHistoricSync(id: Long):HistoricSyncModel
}