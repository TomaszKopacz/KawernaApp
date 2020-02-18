package com.tomaszkopacz.kawernaapp.util.extensions

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}