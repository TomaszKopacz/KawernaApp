package com.tomaszkopacz.kawernaapp.functionalities.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.auth.AuthManager

class SignUpViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    init {
        checkIsUserLoggedIn()
    }

    private fun checkIsUserLoggedIn() {
        if (authManager.getLoggedUser() != null)
            state.postValue(STATE_USER_LOGGED)
    }

    fun registerAttempt(mail: String, password: String) {
        val mailToVerify = mail.trim()
        val passwordToVerify = password.trim()

        if (areDataEmpty(mailToVerify, passwordToVerify)) {
            state.postValue(STATE_REGISTRATION_FAILED)
            return
        }

        authManager.registerUser(mailToVerify, passwordToVerify, registrationListener)
    }

    private fun areDataEmpty(mail: String, password: String) : Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private val registrationListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) { state.postValue(STATE_REGISTRATION_SUCCEED)}
        override fun onFailure(exception: Exception) { state.postValue(STATE_REGISTRATION_FAILED)}
    }


    companion object {
        const val STATE_USER_LOGGED = "USER LOGGED IN"
        const val STATE_REGISTRATION_SUCCEED = "REGISTRATION_SUCCEED"
        const val STATE_REGISTRATION_FAILED = "REGISTRATION_FAILED"
    }
}