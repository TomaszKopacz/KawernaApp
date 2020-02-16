package com.tomaszkopacz.kawernaapp.data.source.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tomaszkopacz.kawernaapp.data.Player
import com.tomaszkopacz.kawernaapp.data.source.LoggedUserStorage
import javax.inject.Inject

class LoggedUserLocalSource @Inject constructor(context: Context) :
    LoggedUserStorage {

    companion object {
        private const val PREF_NAME: String = "SHARED PREFERENCES"
        private const val LOGGED_USER_CODE: String = "logged user"
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
}