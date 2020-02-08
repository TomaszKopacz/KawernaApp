package com.tomaszkopacz.kawernaapp.ui.game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.di.GameComponent
import com.tomaszkopacz.kawernaapp.ui.main.MainActivity
import javax.inject.Inject

const val PERMISSION_REQUEST_CODE = 100

class GameActivity : AppCompatActivity() {

    lateinit var gameComponent: GameComponent

    @Inject
    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        gameComponent = (application as MyApplication).appComponent.gameComponent().create()
        gameComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                Message.CAMERA_ENABLED -> { }
                Message.CAMERA_DISABLED -> {
                    requestCameraPermission()
                }
            }
        })
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
