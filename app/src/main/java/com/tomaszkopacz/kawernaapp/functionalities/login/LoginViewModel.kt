package com.tomaszkopacz.kawernaapp.functionalities.login

import android.util.Log
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

    fun loginAttempt(mail: String, password: String) {
        val mailToVerify = mail.trim()
        val passwordToVerify = password.trim()

        if (areDataEmpty(mailToVerify, passwordToVerify)) {
            state.value = STATE_LOGIN_FAILED
            return
        }

        authManager.loginUser(mailToVerify, passwordToVerify, authListener)
    }

    private fun areDataEmpty(mail: String, password: String) : Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private val authListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) {
            Log.d("Kawerna", "Auth login succeed")

            fireStoreRepository.getPlayerByEmail(user.email!!, object : FireStoreRepository.DownloadPlayerListener{
                override fun onSuccess(player: Player?) {
                    Log.d("Kawerna", "Firestore login succeed")

                    if (player != null) {
                        sharedPrefsRepository.saveLoggedUser(player)
                        state.value = STATE_LOGIN_SUCCEED
                    }  else {
                        Log.d("Kawerna", "Player is null")
                        state.value = STATE_LOGIN_FAILED
                    }
                }

                override fun onFailure(exception: java.lang.Exception) {
                    Log.d("Kawerna", "Firestore login failed")

                    state.value = STATE_LOGIN_FAILED
                }
            })
        }

        override fun onFailure(exception: Exception) {
            Log.d("Kawerna", exception.message)
            state.value = STATE_LOGIN_FAILED
        }
    }

    companion object {
        const val STATE_LOGIN_SUCCEED = "LOGIN SUCCEED"
        const val STATE_LOGIN_FAILED = "LOGIN FAILED"
    }
}