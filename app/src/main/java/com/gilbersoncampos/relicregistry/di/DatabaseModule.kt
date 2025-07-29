package com.gilbersoncampos.relicregistry.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gilbersoncampos.relicregistry.data.local.dao.HistoricSyncDao
import com.gilbersoncampos.relicregistry.data.local.dao.RecordDao
import com.gilbersoncampos.relicregistry.data.local.database.AppDatabase
import com.gilbersoncampos.relicregistry.data.local.database.MIGRATION_1_2
import com.gilbersoncampos.relicregistry.data.local.database.MIGRATION_2_3
import com.gilbersoncampos.relicregistry.data.local.database.MIGRATION_3_4
import com.gilbersoncampos.relicregistry.data.local.database.MIGRATION_4_5
import com.gilbersoncampos.relicregistry.data.local.database.MIGRATION_5_6
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3,MIGRATION_3_4,MIGRATION_4_5,MIGRATION_5_6)
            .build()
    @Provides
    @Singleton
    fun providesRecordDao(database: AppDatabase): RecordDao = database.recordDao()

    @Provides
    @Singleton
    fun providesHistoricSyncDao(database: AppDatabase): HistoricSyncDao = database.historicDao()


}