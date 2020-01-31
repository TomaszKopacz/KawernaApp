package com.tomaszkopacz.kawernaapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.functionalities.gamescores.PlayersScoresViewModel
import com.tomaszkopacz.kawernaapp.functionalities.home.HomeViewModel
import com.tomaszkopacz.kawernaapp.functionalities.login.LoginViewModel
import com.tomaszkopacz.kawernaapp.functionalities.scanplayers.ScanPlayersViewModel
import com.tomaszkopacz.kawernaapp.functionalities.signup.SignUpViewModel
import com.tomaszkopacz.kawernaapp.functionalities.statistics.StatisticsViewModel
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class ViewModelFactory (
    private val authManager: AuthManager,
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(authManager, sharedPrefsRepository, fireStoreRepository)

                isAssignableFrom(SignUpViewModel::class.java) ->
                    SignUpViewModel(authManager, sharedPrefsRepository, fireStoreRepository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(sharedPrefsRepository, fireStoreRepository)

                isAssignableFrom(ScanPlayersViewModel::class.java) ->
                    ScanPlayersViewModel(sharedPrefsRepository, fireStoreRepository)

                isAssignableFrom(PlayersScoresViewModel::class.java) ->
                    PlayersScoresViewModel(fireStoreRepository)

                isAssignableFrom(StatisticsViewModel::class.java) ->
                    StatisticsViewModel(sharedPrefsRepository, fireStoreRepository)

                else -> throw IllegalArgumentException("Unknown ViewModel class ${this.name}")
            }
        } as T
}