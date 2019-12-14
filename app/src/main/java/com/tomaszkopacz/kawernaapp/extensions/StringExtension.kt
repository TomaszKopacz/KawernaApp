package com.tomaszkopacz.kawernaapp.extensions

fun String.isEmailPattern() : Boolean {
    return this.contains('@')
}