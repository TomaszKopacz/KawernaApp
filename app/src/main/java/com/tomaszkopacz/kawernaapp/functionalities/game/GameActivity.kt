package com.tomaszkopacz.kawernaapp.functionalities.game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    fun goToMainActivity() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
