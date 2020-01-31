package com.tomaszkopacz.kawernaapp.functionalities.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class LoginViewModel(
    private val authManager: AuthManager,
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    var state = MutableLiveData<String>()

    fun loginAttempt(mailToVerify: String, passwordToVerify: String) {
        val mailTrimmed = mailToVerify.trim()
        val passwordTrimmed = passwordToVerify.trim()

        if (areDataEmpty(mailTrimmed, passwordTrimmed)) {
            loginFailed()
            return
        }

        authManager.loginUser(mailTrimmed, passwordTrimmed, authListener)
    }

    private fun areDataEmpty(mail: String, password: String): Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private val authListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) {
            getLoggedUserToStoreInMemory(user)
        }

        override fun onFailure(exception: Exception) {
            loginFailed()
        }
    }

    private fun getLoggedUserToStoreInMemory(user: FirebaseUser) {
        fireStoreRepository.getPlayerByEmail(user.email!!, fireStoreListener)
    }

    private val fireStoreListener = object : FireStoreRepository.DownloadPlayerListener {
        override fun onSuccess(player: Player?) {
            if (player != null) {
                saveLoggedUserInMemory(player)
                loginSucceed()

            } else {
                loginFailed()
            }
        }

        override fun onFailure(exception: Exception) {
            loginFailed()
        }
    }

    private fun saveLoggedUserInMemory(player: Player) {
        sharedPrefsRepository.saveLoggedUser(player)
    }

    private fun loginSucceed() {
        state.postValue(STATE_LOGIN_SUCCEED)
    }

    private fun loginFailed() {
        state.postValue(STATE_LOGIN_FAILED)
    }

    companion object {
        const val STATE_LOGIN_SUCCEED = "LOGIN SUCCEED"
        const val STATE_LOGIN_FAILED = "LOGIN FAILED"
    }
}