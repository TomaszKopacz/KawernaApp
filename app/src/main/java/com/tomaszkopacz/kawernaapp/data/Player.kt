package com.tomaszkopacz.kawernaapp.data

data class Player(val email: String) {

    override fun equals(other: Any?): Boolean {
        return if (other is Player) other.email == this.email else false
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}