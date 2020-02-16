package com.tomaszkopacz.kawernaapp.data.source

import com.tomaszkopacz.kawernaapp.data.Player

interface LoggedUserStorage {

    fun setLoggedUser(user: Player)
    fun getLoggedUser(): Player?
    fun clearLoggedUser()
}