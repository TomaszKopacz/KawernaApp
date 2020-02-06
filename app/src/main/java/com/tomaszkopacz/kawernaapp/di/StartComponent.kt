package com.tomaszkopacz.kawernaapp.di

import com.tomaszkopacz.kawernaapp.functionalities.start.login.LoginFragment
import com.tomaszkopacz.kawernaapp.functionalities.start.register.SignUpFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface StartComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StartComponent
    }

    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignUpFragment)
}