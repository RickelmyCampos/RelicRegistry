package com.gilbersoncampos.relicregistry.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.model.StatusSync
import com.gilbersoncampos.relicregistry.data.repository.HistoricSyncRepository
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class SyncWorker @AssistedInject constructor(  @Assisted appContext: Context, @Assisted workerParams: WorkerParameters,private val recordRepository: RecordRepository,private val historicRepository:HistoricSyncRepository):
    CoroutineWorker(appContext, workerParams)  {
    override suspend fun doWork(): Result {
        Log.e("SYNC", "Executando")
        var historic: HistoricSyncModel?=null;
        val listener= object : HistoricListener {
            override suspend fun updateHistoric(historicSyncModel: HistoricSyncModel) {
                historic=historicSyncModel
                historicRepository.updateHistoricSync(historicSyncModel)
            }
        }
        return try {
            recordRepository.syncRecords(listener)
            Result.success()
        } catch (e: Exception) {
            Log.e("SYNC", "Erro ao sincronizar registros: ${e.message}", e)
            listener.updateHistoric(historic?.copy(status = StatusSync.ERROR,errorMessage = e.message)?:HistoricSyncModel(id = 0, startIn = LocalDateTime.now(), endIn = null, status = StatusSync.ERROR, data = "historico nao criado: ${e.message}"))
            Result.failure()
        }
    }
}
interface HistoricListener{
    suspend fun updateHistoric(historicSyncModel: HistoricSyncModel)
}