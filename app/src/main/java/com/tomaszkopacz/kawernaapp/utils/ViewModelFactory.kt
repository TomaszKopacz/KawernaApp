package com.tomaszkopacz.kawernaapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.functionalities.gamescores.PlayersScoresViewModel
import com.tomaszkopacz.kawernaapp.functionalities.home.HomeViewModel

class ViewModelFactory (
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(authManager, fireStoreRepository)

                isAssignableFrom(PlayersScoresViewModel::class.java) ->
                    PlayersScoresViewModel(fireStoreRepository)

                else -> throw IllegalArgumentException("Unknown ViewModel class ${this.name}")
            }
        } as T
}