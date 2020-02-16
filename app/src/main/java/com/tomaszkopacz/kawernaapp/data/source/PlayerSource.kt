package com.tomaszkopacz.kawernaapp.data.source

import com.tomaszkopacz.kawernaapp.data.Player
import java.lang.Exception

interface PlayerSource {

    fun addPlayer(player: Player, listener: PlayerListener?)
    fun getPlayer(email: String, listener: PlayerListener?)

    interface PlayerListener {
        fun onSuccess(player: Player)
        fun onFailure(exception: Exception)
    }
}