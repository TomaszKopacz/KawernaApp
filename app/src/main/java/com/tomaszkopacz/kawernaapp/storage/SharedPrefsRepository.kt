package com.tomaszkopacz.kawernaapp.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tomaszkopacz.kawernaapp.data.Player
import javax.inject.Inject

class SharedPrefsRepository @Inject constructor(context: Context) : Storage {

    companion object {
        private const val PREF_NAME: String = "SHARED PREFERENCES"
        private const val LOGGED_USER_CODE: String = "logged user"
        private const val GAME_ID_CODE: String = "game id"
        private const val GAME_PLAYERS_CODE: String = "players"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun setLoggedUser(user: Player) {
        val userJson: String = Gson().toJson(user)
        sharedPreferences.edit().putString(LOGGED_USER_CODE, userJson).apply()
    }

    override fun getLoggedUser() : Player? {
        val user = sharedPreferences.getString(LOGGED_USER_CODE, null)

        return if (user == null) {
            null

        } else {
            val type = object : TypeToken<Player>() {}.type
            Gson().fromJson(user, type)
        }
    }

    override fun clearLoggedUser() {
        sharedPreferences.edit().remove(LOGGED_USER_CODE).apply()
    }

    override fun setGameId(id: String) {
        sharedPreferences.edit().putString(GAME_ID_CODE, id).apply()
    }

    override fun getGameId() : String? {
        return sharedPreferences.getString(GAME_ID_CODE, null)
    }

    override fun clearGameId() {
        sharedPreferences.edit().remove(GAME_ID_CODE).apply()
    }

    override fun setGamePlayers(players: ArrayList<Player>) {
        val playersJson: String = Gson().toJson(players)
        sharedPreferences.edit().putString(GAME_PLAYERS_CODE, playersJson).apply()
    }

    override fun getGamePlayers() : ArrayList<Player>? {
        val players = sharedPreferences.getString(GAME_PLAYERS_CODE, null)

        val type = object : TypeToken<ArrayList<Player>>() {}.type
        return Gson().fromJson(players, type)
    }

    override fun clearGamePlayers() {
        sharedPreferences.edit().remove(GAME_PLAYERS_CODE).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}