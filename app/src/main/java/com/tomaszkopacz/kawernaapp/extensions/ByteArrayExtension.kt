package com.tomaszkopacz.kawernaapp.extensions

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}