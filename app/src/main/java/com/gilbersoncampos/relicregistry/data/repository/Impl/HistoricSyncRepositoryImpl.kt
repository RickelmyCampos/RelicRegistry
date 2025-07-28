package com.gilbersoncampos.relicregistry.data.repository.Impl

import com.gilbersoncampos.relicregistry.data.local.dao.HistoricSyncDao
import com.gilbersoncampos.relicregistry.data.mapper.toEntity
import com.gilbersoncampos.relicregistry.data.mapper.toModel
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.repository.HistoricSyncRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HistoricSyncRepositoryImpl @Inject constructor(val historycDao: HistoricSyncDao): HistoricSyncRepository {
    override suspend fun createHistoricSync(historic: HistoricSyncModel) {
        val list=getAllHistoricSync().firstOrNull()?: emptyList()
        if(list.size >= 20){
            historycDao.deleteHistoricSync(list.first().toEntity())
        }
        historycDao.createHistoricSync(historic.toEntity())
    }

    override suspend fun getAllHistoricSync(): Flow<List<HistoricSyncModel>> {
        return flow { historycDao.getAllHistoricSync().collect { list -> emit(list.map { it.toModel() }) } }
    }

    override suspend fun updateHistoricSync(historic: HistoricSyncModel) {
        historycDao.updateHistoricSync(historic.toEntity())
    }

    override suspend fun getLastHistoricSync(): HistoricSyncModel {
        return historycDao.getLastHistoricSync().toModel()
    }

    override suspend fun getHistoricSync(id: Long): HistoricSyncModel {
        historycDao.getHistoricSyncById(id).firstOrNull()?.let {
            return it.toModel()
        }?:run{
            throw Exception("Registro não encontrado")

        }
    }
}