package com.tomaszkopacz.kawernaapp.functionalities.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class SignUpViewModel(
    private val authManager: AuthManager,
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private lateinit var mail: String
    private lateinit var name: String
    private lateinit var password: String

    var state = MutableLiveData<String>()

    fun registerAttempt(mailToVerify: String, nameToVerify: String, passwordToVerify: String) {
        val mailTrimmed = mailToVerify.trim()
        val nameTrimmed = nameToVerify.trim()
        val passwordTrimmed = passwordToVerify.trim()

        if (areDataEmpty(mailTrimmed, passwordTrimmed)) {
            registrationFailed()
            return
        }

        mail = mailTrimmed
        name = nameTrimmed
        password = passwordTrimmed

        registerToAuth(mail, password)
    }

    private fun areDataEmpty(mail: String, password: String) : Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private fun registerToAuth(mail: String, password: String) {
        authManager.registerUser(mail, password, registrationAuthListener)
    }

    private val registrationAuthListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) {
            registerToFireStore(mail, name, registrationFireStoreListener)
        }

        override fun onFailure(exception: Exception) {
            registrationFailed()
        }
    }

    private fun registerToFireStore(mail: String, name: String?, listener: FireStoreRepository.UploadPlayerListener) {
        val player = Player(mail)
        player.name = name
        fireStoreRepository.addPlayer(player, listener)
    }

    private val registrationFireStoreListener = object : FireStoreRepository.UploadPlayerListener {
        override fun onSuccess(player: Player) {
            saveLoggedUserInMemory(player)
            registrationSucceed()
        }

        override fun onFailure(exception: Exception) {
            registrationFailed()
        }
    }

    private fun saveLoggedUserInMemory(player: Player) {
        sharedPrefsRepository.saveLoggedUser(player)
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