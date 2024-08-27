package com.gilbersoncampos.relicregistry.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun createRecord(record: CatalogRecordEntity)
    @Query("SELECT * FROM catalog_records")
     fun getAllRecord(): Flow<List<CatalogRecordEntity>>
    @Query("SELECT * FROM catalog_records ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord():CatalogRecordEntity
    @Query("SELECT * FROM catalog_records WHERE id = :id")
     fun getRecordById(id:Long):Flow<CatalogRecordEntity>
    @Update
    suspend fun updateRecord(record: CatalogRecordEntity)
}