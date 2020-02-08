package com.tomaszkopacz.kawernaapp.utils.extensions

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}