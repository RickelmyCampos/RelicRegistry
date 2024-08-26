package com.gilbersoncampos.relicregistry.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gilbersoncampos.relicregistry.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun createRecord(record: RecordEntity)
    @Query("SELECT * FROM record")
     fun getAllRecord(): Flow<List<RecordEntity>>
    @Query("SELECT * FROM record ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord():RecordEntity
    @Query("SELECT * FROM record WHERE id = :id")
     fun getRecordById(id:Int):Flow<RecordEntity>
    @Update
    suspend fun updateRecord(record: RecordEntity)
}