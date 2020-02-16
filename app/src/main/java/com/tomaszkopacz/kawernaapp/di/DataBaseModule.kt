package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.data.source.PlayerSource
import com.tomaszkopacz.kawernaapp.data.source.ScoresSource
import com.tomaszkopacz.kawernaapp.data.source.remote.PlayerRemoteSource
import com.tomaszkopacz.kawernaapp.data.source.remote.ScoresRemoteSource
import dagger.Binds
import dagger.Module

@Module
abstract class DataBaseModule {

    @Binds
    abstract fun providePlayerSource(source: PlayerRemoteSource): PlayerSource

    @Binds
    abstract fun provideScoreSource(source: ScoresRemoteSource): ScoresSource
}