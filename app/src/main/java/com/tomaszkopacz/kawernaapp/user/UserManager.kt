package com.tomaszkopacz.kawernaapp.user

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import com.tomaszkopacz.kawernaapp.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val repository: DataBaseRepository,
    private val storage: Storage
) {

    private var mail: String? = null
    private var name: String? = null
    private var password: String? = null

    private var loginListener: UserListener? = null
    private var registerListener: UserListener? = null

    fun isUserLoggedIn(): Boolean {
        return storage.getLoggedUser() != null
    }

    fun getLoggedUser(): Player? {
        return storage.getLoggedUser()
    }

    fun login(mail: String, password: String, listener: UserListener?) {
        this.mail = mail
        this.password = password

        this.loginListener = listener

        trimCredentials()

        if (credentialsAreCorrect()) {
            attemptUserLogin()
        }
    }

    fun register(mail: String, name: String, password: String, listener: UserListener?) {
        this.mail = mail
        this.name = name
        this.password = password

        this.registerListener = listener

        trimCredentials()

        if (credentialsAreCorrect()) {
            attemptUserRegistration()
        }
    }

    private fun trimCredentials() {
        mail = mail!!.trim()
        password = password!!.trim()
    }

    private fun credentialsAreCorrect(): Boolean {
        return !credentialsAreEmpty()
    }

    private fun credentialsAreEmpty(): Boolean {
        return mail!!.isEmpty() || password!!.isEmpty()
    }

    private fun attemptUserLogin() {
        repository.getPlayerByEmail(mail!!, dbLoginListener)
    }

    private fun attemptUserRegistration() {
        val player = Player(mail!!, name!!, password!!)
        repository.addPlayer(player, dbRegisterListener)
    }

    private val dbLoginListener = object : DataBaseRepository.PlayerListener {
        override fun onSuccess(player: Player) {
            storage.setLoggedUser(player)
            loginListener?.onSuccess(player)
        }

        override fun onFailure(exception: Exception) {
            loginListener?.onFailure(exception)
        }
    }

    private val dbRegisterListener = object : DataBaseRepository.PlayerListener {
        override fun onSuccess(player: Player) {
            storage.setLoggedUser(player)
            registerListener?.onSuccess(player)
        }

        override fun onFailure(exception: Exception) {
            registerListener?.onFailure(exception)
        }
    }

    interface UserListener {
        fun onSuccess(player: Player)
        fun onFailure(exception: Exception)
    }
}