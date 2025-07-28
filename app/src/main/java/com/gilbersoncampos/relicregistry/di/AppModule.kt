package com.gilbersoncampos.relicregistry.di

import com.gilbersoncampos.relicregistry.data.remote.FirebaseDataSource.FirebaseDataSource
import com.gilbersoncampos.relicregistry.data.remote.RemoteDataSource
import com.gilbersoncampos.relicregistry.data.repository.DataAnalysisRepository
import com.gilbersoncampos.relicregistry.data.repository.HistoricSyncRepository
import com.gilbersoncampos.relicregistry.data.repository.Impl.DataAnalysisRepositoryImpl
import com.gilbersoncampos.relicregistry.data.repository.Impl.HistoricSyncRepositoryImpl
import com.gilbersoncampos.relicregistry.data.repository.Impl.RecordRepositoryImpl
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppModule{
    @Binds
    abstract fun bindRecordRepository(impl: RecordRepositoryImpl): RecordRepository
    @Binds
    abstract fun bindDataAnalysisRepository(impl: DataAnalysisRepositoryImpl): DataAnalysisRepository
    @Binds
    abstract fun bindRemoteDataSource(impl: FirebaseDataSource): RemoteDataSource
    @Binds
    abstract fun bindHistoricSyncRepository(impl: HistoricSyncRepositoryImpl): HistoricSyncRepository
}