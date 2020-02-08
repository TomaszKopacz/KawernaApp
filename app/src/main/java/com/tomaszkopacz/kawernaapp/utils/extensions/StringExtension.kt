package com.tomaszkopacz.kawernaapp.utils.extensions

import java.security.MessageDigest

fun String.MD5() : String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}