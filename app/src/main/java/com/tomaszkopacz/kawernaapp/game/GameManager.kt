package com.tomaszkopacz.kawernaapp.game

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import com.tomaszkopacz.kawernaapp.storage.Storage

class GameManager(
    private val repository: DataBaseRepository,
    private val storage: Storage
) {

    private var gameId: String? = null
    private var players = ArrayList<Player>()

    private var playerListener: PlayerListener? = null

    init {
        gameId = generateUniqueGameId()
    }

    private fun generateUniqueGameId(): String {
        val millis = System.currentTimeMillis().toString()
        val random = (0..1000000).random().toString()
        return millis + random
    }

    fun addNewPlayer(email: String, listener: PlayerListener?){
        this.playerListener = listener
        getPlayerByEmail(email)
    }

    private fun getPlayerByEmail(email: String) {
        repository.getPlayerByEmail(email, dbPlayerListener)
    }

    private val dbPlayerListener = object : DataBaseRepository.PlayerListener {
        override fun onSuccess(player: Player) {
            if (!players.contains(player)) {
                players.add(player)
            }

            playerListener?.onSuccess(player)
        }

        override fun onFailure(exception: Exception) {
            playerListener?.onFailure(exception)
        }
    }

    fun confirmPlayers() {
        saveGameToStorage()
    }

    private fun saveGameToStorage() {
        storage.setGameId(gameId!!)
        storage.setGamePlayers(players)
    }

    fun getPlayers(): ArrayList<Player> {
        return players
    }

    interface PlayerListener {
        fun onSuccess(player: Player)
        fun onFailure(exception: Exception)
    }
}