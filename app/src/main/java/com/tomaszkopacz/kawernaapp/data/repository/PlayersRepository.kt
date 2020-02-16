package com.tomaszkopacz.kawernaapp.data.repository

import com.tomaszkopacz.kawernaapp.data.Player
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

    fun findUserByEmail(email: String, listener: PlayerSource.PlayerListener?) {
        playerSource.getPlayer(email, listener)
    }

    fun loginUser(user: Player) {
        loggedUserStorage.setLoggedUser(user)
    }

    fun logoutUser() {
        loggedUserStorage.clearLoggedUser()
    }

    fun registerUser(user: Player, listener: PlayerSource.PlayerListener?) {
        playerSource.addPlayer(user, listener)
        loggedUserStorage.setLoggedUser(user)
    }

}