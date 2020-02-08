package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.ui.start.StartActivity
import com.tomaszkopacz.kawernaapp.ui.start.login.LoginFragment
import com.tomaszkopacz.kawernaapp.ui.start.register.SignUpFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface StartComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StartComponent
    }

    fun inject(activity: StartActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignUpFragment)
}