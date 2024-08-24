package com.gilbersoncampos.relicregistry.di

import com.gilbersoncampos.relicregistry.data.repository.Impl.RecordRepositoryImpl
import com.gilbersoncampos.relicregistry.data.repository.RecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal abstract class AppModule{
    @Binds
    abstract fun bindRecordRepository(impl: RecordRepositoryImpl): RecordRepository
}