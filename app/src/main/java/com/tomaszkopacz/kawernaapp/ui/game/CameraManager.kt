package com.tomaszkopacz.kawernaapp.ui.game

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.checkSelfPermission
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraManager @Inject constructor(private val context: Context) {

    fun isCameraEnabled(): Boolean {
        return (checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
    }
}