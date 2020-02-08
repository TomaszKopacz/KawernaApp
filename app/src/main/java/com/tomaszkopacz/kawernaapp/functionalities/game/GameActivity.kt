package com.tomaszkopacz.kawernaapp.functionalities.game

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.di.GameComponent
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity

const val PERMISSION_REQUEST_CODE = 100

class GameActivity : AppCompatActivity() {

    lateinit var gameComponent: GameComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        gameComponent = (application as MyApplication).appComponent.gameComponent().create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> { }
        }
    }

    fun goToMainActivity() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
