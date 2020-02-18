package com.tomaszkopacz.kawernaapp.managers

import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.repository.PlayersRepository
import com.tomaszkopacz.kawernaapp.util.extensions.MD5
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

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun getLoggedUser(): Player? {
        return repository.getLoggedUser()
    }

    suspend fun login(mail: String, password: String): Result<Player> {
        this.mail = mail
        this.password = password

        trimCredentials()

        return if (credentialsAreCorrect()) {
            attemptUserLogin()

        } else {
            Result.Failure(Message(Message.EMPTY_DATA))
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

    private suspend fun attemptUserLogin(): Result<Player> {
        if (networkManager.isNetworkConnected()) {

            return when (val result = repository.findUserByEmail(mail!!)) {
                is Result.Success -> {
                    val player = result.data
                    checkPassword(player)
                }

                is Result.Failure -> {
                    Result.Failure(Message(Message.LOGIN_EMAIL_NOT_FOUND))
                }

                else -> {
                    Result.Failure(Message(Message.LOGIN_FAILED))
                }
            }

        } else {
            return Result.Failure(Message(Message.NO_INTERNET_CONNECTION))
        }
    }

    private fun checkPassword(player: Player): Result<Player> {
        return if (password!!.MD5() == player.password) {
            repository.loginUser(player)
            Result.Success(player)

        } else {
            Result.Failure(Message(Message.PASSWORD_INCORRECT))
        }
    }

    fun logout() {
        repository.logoutUser()
    }

    suspend fun register(mail: String, name: String, password: String): Result<Player> {
        this.mail = mail
        this.name = name
        this.password = password

        trimCredentials()

        return if (credentialsAreCorrect()) {
            attemptUserRegistration()

        } else {
            Result.Failure(Message(Message.EMPTY_DATA))
        }
    }

    private suspend fun attemptUserRegistration(): Result<Player> {
        return if(networkManager.isNetworkConnected()) {
            if (userAlreadyExists()) {
                Result.Failure(Message(Message.EMAIL_OCCUPIED))

            } else {
                doRegister()
            }

        } else {
            Result.Failure(Message(Message.NO_INTERNET_CONNECTION))
        }
    }

    private suspend fun userAlreadyExists(): Boolean {
        return when (repository.findUserByEmail(mail!!)) {
            is Result.Success -> {
                true
            }

            is Result.Failure -> {
                false
            }
        }
    }

    private suspend fun doRegister(): Result<Player> {
        val passwordEncrypted = password!!.MD5()
        val newPlayer = Player(mail!!, name!!, passwordEncrypted)
        return repository.registerUser(newPlayer)
    }


}