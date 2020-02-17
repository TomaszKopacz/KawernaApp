package com.tomaszkopacz.kawernaapp.data.repository

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.data.source.LoggedUserStorage
import com.tomaszkopacz.kawernaapp.data.source.PlayerSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersRepository @Inject constructor(
    private val loggedUserStorage: LoggedUserStorage,
    private val playerSource: PlayerSource
) {

    fun isUserLoggedIn(): Boolean {
        return loggedUserStorage.getLoggedUser() != null
    }

    fun getLoggedUser(): Player? {
        return loggedUserStorage.getLoggedUser()
    }

    suspend fun findUserByEmail(email: String): Result<Player> {
        return playerSource.getPlayer(email)
    }

    fun loginUser(user: Player) {
        loggedUserStorage.setLoggedUser(user)
    }

    fun logoutUser() {
        loggedUserStorage.clearLoggedUser()
    }

    suspend fun registerUser(user: Player): Result<Player>{
        loggedUserStorage.setLoggedUser(user)
        return playerSource.addPlayer(user)
    }
}