package com.tomaszkopacz.kawernaapp.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tomaszkopacz.kawernaapp.data.Player

class SharedPrefsRepository private constructor() : Storage {

    companion object {
        private const val PREF_NAME: String = "SHARED PREFERENCES"
        private const val LOGGED_USER_CODE: String = "logged user"
        private const val GAME_ID_CODE: String = "game id"
        private const val GAME_PLAYERS_CODE: String = "players"

        private val sharedPrefsManager  = SharedPrefsRepository()
        private lateinit var sharedPrefs: SharedPreferences

        fun getInstance(context: Context) : SharedPrefsRepository {
            if (!::sharedPrefs.isInitialized){
                synchronized(SharedPrefsRepository::class.java) {
                    sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                }
            }

            return sharedPrefsManager
        }
    }

    override fun setLoggedUser(user: Player) {
        val userJson: String = Gson().toJson(user)
        sharedPrefs.edit().putString(LOGGED_USER_CODE, userJson).apply()
    }

    override fun getLoggedUser() : Player? {
        val user = sharedPrefs.getString(LOGGED_USER_CODE, null)

        return if (user == null) {
            null

        } else {
            val type = object : TypeToken<Player>() {}.type
            Gson().fromJson(user, type)
        }
    }

    override fun clearLoggedUser() {
        sharedPrefs.edit().remove(LOGGED_USER_CODE).apply()
    }

    override fun setGameId(id: String) {
        sharedPrefs.edit().putString(GAME_ID_CODE, id).apply()
    }

    override fun getGameId() : String? {
        return sharedPrefs.getString(GAME_ID_CODE, null)
    }

    override fun clearGameId() {
        sharedPrefs.edit().remove(GAME_ID_CODE).apply()
    }

    override fun setGamePlayers(players: ArrayList<Player>) {
        val playersJson: String = Gson().toJson(players)
        sharedPrefs.edit().putString(GAME_PLAYERS_CODE, playersJson).apply()
    }

    override fun getGamePlayers() : ArrayList<Player>? {
        val players = sharedPrefs.getString(GAME_PLAYERS_CODE, null)

        val type = object : TypeToken<ArrayList<Player>>() {}.type
        return Gson().fromJson(players, type)
    }

    override fun clearGamePlayers() {
        sharedPrefs.edit().remove(GAME_PLAYERS_CODE).apply()
    }

    fun clearAll() {
        sharedPrefs.edit().clear().apply()
    }
}