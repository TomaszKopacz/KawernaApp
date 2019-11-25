package com.tomaszkopacz.kawernaapp.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tomaszkopacz.kawernaapp.data.Player

class SharedPrefsManager private constructor() {

    companion object {
        private const val PREF_NAME: String = "com.tomaszkopacz.kawernaapp"
        private const val PLAYERS_CODE: String = "players"

        private val sharedPrefsManager  = SharedPrefsManager()
        private lateinit var sharedPrefs: SharedPreferences

        fun getInstance(context: Context) : SharedPrefsManager {
            if (!::sharedPrefs.isInitialized){
                synchronized(SharedPrefsManager::class.java) {
                    sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                }
            }

            return sharedPrefsManager
        }
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
}