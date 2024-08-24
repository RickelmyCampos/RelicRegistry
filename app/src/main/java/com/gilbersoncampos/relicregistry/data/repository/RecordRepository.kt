package com.gilbersoncampos.relicregistry.data.repository

import com.gilbersoncampos.relicregistry.data.model.RecordModel

interface RecordRepository{
    suspend fun createRecord(record: RecordModel)
    suspend fun getAllRecord():List<RecordModel>
    suspend fun getLastRecord():RecordModel
    suspend fun getRecordById(id:Int):RecordModel

}