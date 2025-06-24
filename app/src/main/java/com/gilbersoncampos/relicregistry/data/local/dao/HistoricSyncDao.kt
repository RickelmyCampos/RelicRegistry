package com.gilbersoncampos.relicregistry.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.local.entity.HistoricSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoricSyncDao {
    @Insert
    suspend fun createHistoricSync(historic:HistoricSyncEntity)

    @Query("SELECT * FROM historic_sync")
    fun getAllHistoricSync(): Flow<List<HistoricSyncEntity>>

    @Update
    suspend fun updateHistoricSync(record: HistoricSyncEntity)

    @Delete
    suspend fun deleteRecord(record: HistoricSyncEntity)

    @Query("SELECT * FROM historic_sync ORDER BY id DESC LIMIT 1")
    suspend fun getLastHistoricSync():HistoricSyncEntity

    @Query("SELECT * FROM historic_sync WHERE id = :id")
    fun getHistoricSyncById(id: Long): Flow<HistoricSyncEntity>


}


