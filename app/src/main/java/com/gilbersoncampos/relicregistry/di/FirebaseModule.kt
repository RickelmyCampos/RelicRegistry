package com.gilbersoncampos.relicregistry.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object FirebaseModule{
    @ActivityScoped
    fun provideFirebaseDb()=Firebase.firestore
}