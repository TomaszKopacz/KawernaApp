package com.tomaszkopacz.kawernaapp.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tomaszkopacz.kawernaapp.data.Player

class SharedPrefsGameManager private constructor() {

    companion object {
        private const val PREF_NAME: String = "GAME"
        private const val GAME_ID_CODE: String = "game_id"
        private const val PLAYERS_CODE: String = "players"

        private val sharedPrefsManager  = SharedPrefsGameManager()
        private lateinit var sharedPrefs: SharedPreferences

        fun getInstance(context: Context) : SharedPrefsGameManager {
            if (!::sharedPrefs.isInitialized){
                synchronized(SharedPrefsGameManager::class.java) {
                    sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                }
            }

            return sharedPrefsManager
        }
    }

    fun saveGameId(id: String) {
        sharedPrefs.edit().putString(GAME_ID_CODE, id).apply()
    }

    fun getGameId() : String? {
        return sharedPrefs.getString(GAME_ID_CODE, null)
    }

    fun clearGameId() {
        sharedPrefs.edit().remove(GAME_ID_CODE).apply()
    }

    fun savePlayers(players: ArrayList<Player>) {
        val playersJson: String = Gson().toJson(players)
        sharedPrefs.edit().putString(PLAYERS_CODE, playersJson).apply()
    }

    fun getPlayers() : ArrayList<Player>? {
        val players = sharedPrefs.getString(PLAYERS_CODE, null)

        val type = object : TypeToken<ArrayList<Player>>() {}.type
        return Gson().fromJson(players, type)
    }

    fun clearPlayers() {
        sharedPrefs.edit().remove(PLAYERS_CODE).apply()
    }

    fun clearAll() {
        sharedPrefs.edit().clear().apply()
    }
}