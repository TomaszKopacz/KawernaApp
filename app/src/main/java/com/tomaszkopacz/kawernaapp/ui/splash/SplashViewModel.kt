package com.tomaszkopacz.kawernaapp.ui.splash


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.managers.UserManager
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    init {
        checkIsUserLoggedIn()
    }

    private fun checkIsUserLoggedIn() {
        if (userManager.isUserLoggedIn()) {
            userIsLoggedIn()

        } else {
            noUserIsLoggedIn()
        }
    }

    private fun userIsLoggedIn() {
        state.postValue(STATE_USER_LOGGED_IN)
    }

    private fun noUserIsLoggedIn() {
        state.postValue(STATE_NO_USER_LOGGED_IN)
    }

    companion object {
        const val STATE_USER_LOGGED_IN = "User is logged in"
        const val STATE_NO_USER_LOGGED_IN = "No user is logged in"
    }
}