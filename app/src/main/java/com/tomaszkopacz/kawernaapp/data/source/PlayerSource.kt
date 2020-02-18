package com.tomaszkopacz.kawernaapp.data.source

import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.Result
import java.lang.Exception

interface PlayerSource {

    suspend fun addPlayer(player: Player): Result<Player>
    suspend fun getPlayer(email: String): Result<Player>
    suspend fun updatePassword(email: String, password: String): Result<Player>
}