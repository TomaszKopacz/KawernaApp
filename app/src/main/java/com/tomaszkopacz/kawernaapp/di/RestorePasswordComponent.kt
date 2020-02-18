package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.ui.password.RestorePasswordActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface RestorePasswordComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RestorePasswordComponent
    }

    fun inject(activity: RestorePasswordActivity)
}