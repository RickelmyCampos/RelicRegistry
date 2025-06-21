package com.gilbersoncampos.relicregistry.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.local.entity.FilterDataChart
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun createRecord(record: CatalogRecordEntity)

    @Query("SELECT * FROM catalog_records")
    fun getAllRecord(): Flow<List<CatalogRecordEntity>>

    @Query("SELECT * FROM catalog_records ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord(): CatalogRecordEntity

    @Query("SELECT * FROM catalog_records WHERE id = :id")
    fun getRecordById(id: Long): Flow<CatalogRecordEntity>

    @Query("SELECT * FROM catalog_records WHERE idRemote like :id")
    fun getRecordByIdRemote(id: String): Flow<CatalogRecordEntity>

    @Update
    suspend fun updateRecord(record: CatalogRecordEntity)

    @Delete
    suspend fun deleteRecord(record: CatalogRecordEntity)

    @Query("DELETE FROM catalog_records WHERE id IN (:ids)")
    suspend fun deleteRecords(ids: List<Long>)

    @Query("SELECT COUNT(id) as value,:filter as label FROM catalog_records GROUP BY :filter")
    fun fetchFilter(filter:String): Flow<List<FilterDataChart>>
    @Query("SELECT archaeologicalSite FROM catalog_records GROUP BY archaeologicalSite")
    fun getAllArchaeologicalSite(): Flow<List<String>>
}