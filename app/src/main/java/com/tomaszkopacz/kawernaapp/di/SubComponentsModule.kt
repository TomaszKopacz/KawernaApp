package com.tomaszkopacz.kawernaapp.di

import dagger.Module

@Module(
    subcomponents = [
        SplashComponent::class,
        StartComponent::class,
        RestorePasswordComponent::class,
        MainComponent::class,
        GameComponent::class
    ]
)
class SubComponentsModule