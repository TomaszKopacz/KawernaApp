package com.tomaszkopacz.kawernaapp.database

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Score
import java.lang.Exception

interface DataBaseRepository {

    fun addPlayer(player: Player, listener: PlayerListener?)
    fun getPlayerByEmail(email: String, listener: PlayerListener?)

    interface PlayerListener {
        fun onSuccess(player: Player)
        fun onFailure(exception: Exception)
    }

    fun getUsersScores(player: Player, listener: ScoresListener?)

    interface ScoresListener {
        fun onSuccess(scores: ArrayList<Score>)
        fun onFailure(exception: Exception)
    }
}