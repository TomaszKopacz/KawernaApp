package com.tomaszkopacz.kawernaapp.ui.main

import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.managers.UserManager
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userManager: UserManager): ViewModel() {
    fun logout() {
        userManager.logout()
    }
}