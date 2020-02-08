package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.ui.splash.SplashScreenActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SplashComponent
    }

    fun inject(activity: SplashScreenActivity)
}