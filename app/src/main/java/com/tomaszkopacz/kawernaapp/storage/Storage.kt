package com.tomaszkopacz.kawernaapp.storage

import com.tomaszkopacz.kawernaapp.data.Player

interface Storage {

    fun setLoggedUser(user: Player)
    fun getLoggedUser(): Player?
    fun clearLoggedUser()
}