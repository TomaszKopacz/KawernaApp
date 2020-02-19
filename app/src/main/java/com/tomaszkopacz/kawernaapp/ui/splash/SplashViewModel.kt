package com.tomaszkopacz.kawernaapp.ui.splash


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
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
        state.postValue(Message.STATE_USER_LOGGED_IN)
    }

    private fun noUserIsLoggedIn() {
        state.postValue(Message.STATE_NO_USER_LOGGED_IN)
    }
}