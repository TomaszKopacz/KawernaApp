package com.tomaszkopacz.kawernaapp.functionalities.main

import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.user.UserManager
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userManager: UserManager): ViewModel() {
    fun logout() {
        userManager.logout()
    }
}