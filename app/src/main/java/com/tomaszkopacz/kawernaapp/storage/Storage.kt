package com.tomaszkopacz.kawernaapp.storage

import com.tomaszkopacz.kawernaapp.data.Player

interface Storage {

    fun setLoggedUser(user: Player)
    fun getLoggedUser(): Player?
    fun clearLoggedUser()

    fun setGameId(id: String)
    fun getGameId(): String?
    fun clearGameId()

    fun setGamePlayers(players: ArrayList<Player>)
    fun getGamePlayers() : ArrayList<Player>?
    fun clearGamePlayers()
}