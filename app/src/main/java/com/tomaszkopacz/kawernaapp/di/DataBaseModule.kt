package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataBaseModule {
    @Binds
    abstract fun provideDataBase(db: FireStoreRepository): DataBaseRepository
}