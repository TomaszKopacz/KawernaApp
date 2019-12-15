package com.tomaszkopacz.kawernaapp.functionalities.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.auth.AuthManager

class LoginViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun loginAttempt(mail: String, password: String) {
        val mailToVerify = mail.trim()
        val passwordToVerify = password.trim()

        if (areDataEmpty(mailToVerify, passwordToVerify)) {
            state.value = STATE_LOGIN_FAILED
            return
        }

        authManager.loginUser(mailToVerify, passwordToVerify, loginListener)
    }

    private fun areDataEmpty(mail: String, password: String) : Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private val loginListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) { state.value = STATE_LOGIN_SUCCEED }
        override fun onFailure(exception: Exception) { state.value = STATE_LOGIN_FAILED }
    }

    companion object {
        const val STATE_LOGIN_SUCCEED = "LOGIN SUCCEED"
        const val STATE_LOGIN_FAILED = "LOGIN FAILED"
    }
}