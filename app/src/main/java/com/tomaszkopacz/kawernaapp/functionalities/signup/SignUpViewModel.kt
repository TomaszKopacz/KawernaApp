package com.tomaszkopacz.kawernaapp.functionalities.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player

class SignUpViewModel(
    private val authManager: AuthManager,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    var state = MutableLiveData<String>()

    init {
        checkIsUserLoggedIn()
    }

    private fun checkIsUserLoggedIn() {
        if (authManager.getLoggedUser() != null)
            state.postValue(STATE_USER_LOGGED)
    }

    fun registerAttempt(mail: String, name: String, password: String) {
        val mailToVerify = mail.trim()
        val passwordToVerify = password.trim()

        if (areDataEmpty(mailToVerify, passwordToVerify)) {
            state.postValue(STATE_REGISTRATION_FAILED)
            return
        }

        register(mailToVerify, passwordToVerify, name)
    }

    private fun areDataEmpty(mail: String, password: String) : Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private fun register(mail: String, password: String, name: String) {
        authManager.registerUser(mail, password, registrationAuthListener)

        val player = Player(mail)
        player.name = name
        fireStoreRepository.addPlayer(player, registrationRepoListener)
    }

    private val registrationAuthListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) { state.postValue(STATE_REGISTRATION_SUCCEED) }
        override fun onFailure(exception: Exception) { state.postValue(STATE_REGISTRATION_FAILED)}
    }

    private val registrationRepoListener = object : FireStoreRepository.UploadPlayerListener {
        override fun onSuccess(player: Player) { state.postValue(STATE_REGISTRATION_SUCCEED) }
        override fun onFailure(exception: Exception) { state.postValue(STATE_REGISTRATION_FAILED) }
    }


    companion object {
        const val STATE_USER_LOGGED = "USER LOGGED IN"
        const val STATE_REGISTRATION_SUCCEED = "REGISTRATION_SUCCEED"
        const val STATE_REGISTRATION_FAILED = "REGISTRATION_FAILED"
    }
}