package com.gilbersoncampos.relicregistry.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(  @Assisted appContext: Context, @Assisted workerParams: WorkerParameters,private val recordRepository: RecordRepository):
    CoroutineWorker(appContext, workerParams)  {
    override suspend fun doWork(): Result {
        return try {
            recordRepository.syncRecords()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }


}