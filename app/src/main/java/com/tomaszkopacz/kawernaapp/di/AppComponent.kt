package com.tomaszkopacz.kawernaapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubComponentsModule::class, StorageModule::class, DataBaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun splashComponent(): SplashComponent.Factory
    fun startComponent(): StartComponent.Factory
    fun mainComponent(): MainComponent.Factory
    fun gameComponent(): GameComponent.Factory

}