package com.gilbersoncampos.relicregistry.data.repository.Impl

import android.util.Log
import com.gilbersoncampos.relicregistry.data.local.dao.RecordDao
import com.gilbersoncampos.relicregistry.data.mapper.toEntity
import com.gilbersoncampos.relicregistry.data.mapper.toModel
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(private val recordDao: RecordDao) :
    RecordRepository {
    override suspend fun createRecord(record: CatalogRecordModel) {
        recordDao.createRecord(record.toEntity())
        Log.d(javaClass.simpleName, "createRecord: $record")
    }

    override suspend fun getAllRecord(): Flow<List<CatalogRecordModel>> {
        return flow { recordDao.getAllRecord().collect { list -> emit(list.map { it.toModel() }) } }
    }

    override suspend fun getLastRecord(): CatalogRecordModel {
        return recordDao.getLastRecord().toModel()
    }

    override suspend fun getRecordById(id: Long): Flow<CatalogRecordModel> {
        return flow { recordDao.getRecordById(id).collect { record -> emit(record.toModel()) } }
    }

    override suspend fun updateRecord(record: CatalogRecordModel) {
        recordDao.updateRecord(record.toEntity())
    }

    override suspend fun removeRecords(recordList: List<CatalogRecordModel>) {
        val idList = recordList.map { it.id }
        recordDao.deleteRecords(idList)
    }
}