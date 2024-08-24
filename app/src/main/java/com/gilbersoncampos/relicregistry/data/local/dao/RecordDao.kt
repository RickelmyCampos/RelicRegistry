package com.gilbersoncampos.relicregistry.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gilbersoncampos.relicregistry.data.local.entity.RecordEntity

@Dao
interface RecordDao {
    @Insert
    suspend fun createRecord(record: RecordEntity)
    @Query("SELECT * FROM record")
    suspend fun getAllRecord():List<RecordEntity>
    @Query("SELECT * FROM record ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord():RecordEntity
    @Query("SELECT * FROM record WHERE id = :id")
    suspend fun getRecordById(id:Int):RecordEntity
}