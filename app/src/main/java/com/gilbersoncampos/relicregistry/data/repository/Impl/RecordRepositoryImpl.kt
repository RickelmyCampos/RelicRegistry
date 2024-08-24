package com.gilbersoncampos.relicregistry.data.repository.Impl

import android.util.Log
import com.gilbersoncampos.relicregistry.data.local.dao.RecordDao
import com.gilbersoncampos.relicregistry.data.mapper.toEntity
import com.gilbersoncampos.relicregistry.data.mapper.toModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(private val recordDao: RecordDao) :
    RecordRepository {
    override suspend fun createRecord(record: RecordModel) {
        recordDao.createRecord(record.toEntity())
        Log.d(javaClass.simpleName, "createRecord: $record")
    }

    override suspend fun getAllRecord(): List<RecordModel> {
        return recordDao.getAllRecord().map { it.toModel() }
    }

    override suspend fun getLastRecord(): RecordModel {
        return recordDao.getLastRecord().toModel()
    }

    override suspend fun getRecordById(id: Int): RecordModel {
       return recordDao.getRecordById(id).toModel()
    }
}