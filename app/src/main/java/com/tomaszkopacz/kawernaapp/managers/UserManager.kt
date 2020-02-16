package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Message.Companion.EMAIL_OCCUPIED
import com.tomaszkopacz.kawernaapp.data.Message.Companion.EMPTY_DATA
import com.tomaszkopacz.kawernaapp.data.Message.Companion.LOGIN_SUCCEED
import com.tomaszkopacz.kawernaapp.data.Message.Companion.NO_INTERNET_CONNECTION
import com.tomaszkopacz.kawernaapp.data.Message.Companion.PASSWORD_INCORRECT
import com.tomaszkopacz.kawernaapp.data.Message.Companion.LOGIN_EMAIL_NOT_FOUND
import com.tomaszkopacz.kawernaapp.data.Message.Companion.REGISTRATION_SUCCEED
import com.tomaszkopacz.kawernaapp.data.repository.PlayersRepository
import com.tomaszkopacz.kawernaapp.data.source.PlayerSource
import com.tomaszkopacz.kawernaapp.extensions.MD5
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val repository: PlayersRepository,
    private val networkManager: NetworkManager
) {

    private var mail: String? = null
    private var name: String? = null
    private var password: String? = null

    private var loginListener: UserListener? = null
    private var registerListener: UserListener? = null

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun getLoggedUser(): Player? {
        return repository.getLoggedUser()
    }

    fun login(mail: String, password: String, listener: UserListener?) {
        this.mail = mail
        this.password = password

        this.loginListener = listener

        trimCredentials()

        if (credentialsAreCorrect()) {
            attemptUserLogin()

        } else {
            loginListener?.onFailure(Message(EMPTY_DATA))
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
        if (networkManager.isNetworkConnected()) {
            repository.findUserByEmail(mail!!, loginPlayerListener)

        } else {
            loginListener?.onFailure(
                Message(
                    NO_INTERNET_CONNECTION
                )
            )
        }
    }

    private val loginPlayerListener = object : PlayerSource.PlayerListener {
        override fun onSuccess(player: Player) {

            if (password!!.MD5() == player.password) {
                repository.loginUser(player)
                loginListener?.onSuccess(player,
                    Message(LOGIN_SUCCEED)
                )

            } else {
                loginListener?.onFailure(
                    Message(
                        PASSWORD_INCORRECT
                    )
                )
            }
        }

        override fun onFailure(exception: Exception) {
            loginListener?.onFailure(
                Message(
                    LOGIN_EMAIL_NOT_FOUND
                )
            )
        }
    }

    fun logout() {
        repository.logoutUser()
    }

    fun register(mail: String, name: String, password: String, listener: UserListener?) {
        this.mail = mail
        this.name = name
        this.password = password

        this.registerListener = listener

        trimCredentials()

        if (credentialsAreCorrect()) {
            attemptUserRegistration()

        } else {
            registerListener?.onFailure(Message(EMPTY_DATA))
        }
    }

    private fun attemptUserRegistration() {
        if(networkManager.isNetworkConnected()) {
            checkUserAlreadyExists()

        } else {
            registerListener?.onFailure(
                Message(
                    NO_INTERNET_CONNECTION
                )
            )
        }
    }

    private fun checkUserAlreadyExists() {
        repository.findUserByEmail(mail!!, object : PlayerSource.PlayerListener {
            override fun onSuccess(player: Player) {
                registerListener?.onFailure(
                    Message(
                        EMAIL_OCCUPIED
                    )
                )
            }

            override fun onFailure(exception: Exception) {
                val passwordEncrypted = password!!.MD5()
                val player = Player(mail!!, name!!, passwordEncrypted)
                repository.registerUser(player, registerPlayerListener)
            }
        })
    }

    private val registerPlayerListener = object : PlayerSource.PlayerListener {
        override fun onSuccess(player: Player) {
            registerListener?.onSuccess(player,
                Message(REGISTRATION_SUCCEED)
            )
        }

        override fun onFailure(exception: Exception) {
            registerListener?.onFailure(
                Message(
                    NO_INTERNET_CONNECTION
                )
            )
        }
    }

    interface UserListener {
        fun onSuccess(player: Player, message: Message)
        fun onFailure(message: Message)
    }
}