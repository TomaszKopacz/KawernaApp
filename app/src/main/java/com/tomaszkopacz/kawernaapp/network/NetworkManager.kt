package com.tomaszkopacz.kawernaapp.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor(context: Context) {
    private val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkConnected(): Boolean {
        return manager.activeNetworkInfo != null && manager.activeNetworkInfo.isConnected
    }
}