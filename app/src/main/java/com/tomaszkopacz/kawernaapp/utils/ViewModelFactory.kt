package com.tomaszkopacz.kawernaapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.functionalities.gamescores.PlayersScoresViewModel
import com.tomaszkopacz.kawernaapp.functionalities.home.HomeViewModel
import com.tomaszkopacz.kawernaapp.functionalities.login.LoginViewModel
import com.tomaszkopacz.kawernaapp.functionalities.signup.SignUpViewModel

class ViewModelFactory (
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(authManager)

                isAssignableFrom(SignUpViewModel::class.java) ->
                    SignUpViewModel(authManager)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(authManager, fireStoreRepository)

                isAssignableFrom(PlayersScoresViewModel::class.java) ->
                    PlayersScoresViewModel(fireStoreRepository)

                else -> throw IllegalArgumentException("Unknown ViewModel class ${this.name}")
            }
        } as T
}