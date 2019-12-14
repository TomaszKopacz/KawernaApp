package com.tomaszkopacz.kawernaapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository

class ViewModelFactory (
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(AuthManager::class.java, FireStoreRepository::class.java)
            .newInstance(authManager, fireStoreRepository)
    }
}