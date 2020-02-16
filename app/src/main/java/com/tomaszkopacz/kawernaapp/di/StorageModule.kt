package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.data.source.local.LoggedUserLocalSource
import com.tomaszkopacz.kawernaapp.data.source.LoggedUserStorage
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: LoggedUserLocalSource): LoggedUserStorage
}