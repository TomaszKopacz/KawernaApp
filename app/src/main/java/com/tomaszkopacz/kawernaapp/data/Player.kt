package com.tomaszkopacz.kawernaapp.data

data class Player(val email: String, val name: String, val password: String) {

    constructor() : this("", "", "")
}