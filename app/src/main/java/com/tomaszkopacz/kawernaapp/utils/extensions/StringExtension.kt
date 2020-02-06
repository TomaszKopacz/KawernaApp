package com.tomaszkopacz.kawernaapp.utils.extensions

fun String.isEmailPattern() : Boolean {
    return this.contains('@')
}