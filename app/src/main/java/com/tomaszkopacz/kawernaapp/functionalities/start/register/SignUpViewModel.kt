package com.tomaszkopacz.kawernaapp.functionalities.start.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.user.UserManager
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun register(mail: String, name: String, password: String) {
        userManager.register(mail, name, password, registerListener)
    }

    private val registerListener = object : UserManager.UserListener {
        override fun onSuccess(player: Player) {
            registrationSucceed()
        }

        override fun onFailure(exception: Exception) {
            registrationFailed()
        }
    }

    private fun registrationSucceed() {
        state.postValue(STATE_REGISTRATION_SUCCEED)
    }

    private fun registrationFailed() {
        state.postValue(STATE_REGISTRATION_FAILED)
    }

    companion object {
        const val STATE_REGISTRATION_SUCCEED = "REGISTRATION_SUCCEED"
        const val STATE_REGISTRATION_FAILED = "REGISTRATION_FAILED"
    }
}