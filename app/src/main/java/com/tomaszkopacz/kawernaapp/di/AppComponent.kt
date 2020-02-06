package com.tomaszkopacz.kawernaapp.di

import android.content.Context
import com.tomaszkopacz.kawernaapp.functionalities.main.board.HomeFragment
import com.tomaszkopacz.kawernaapp.functionalities.main.profile.AccountFragment
import com.tomaszkopacz.kawernaapp.functionalities.main.statistics.StatisticsFragment
import com.tomaszkopacz.kawernaapp.functionalities.splash.SplashScreenActivity
import com.tomaszkopacz.kawernaapp.functionalities.start.login.LoginFragment
import com.tomaszkopacz.kawernaapp.functionalities.start.register.SignUpFragment
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