package com.tomaszkopacz.kawernaapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        process()
    }

    private fun process() {
        if (isAnyUserLoggedIn()) goToMainActivity()
        else goToStartActivity()
    }

    private fun isAnyUserLoggedIn() : Boolean {
        return SharedPrefsRepository.getInstance(this).getLoggedUser() != null
    }

    private fun goToMainActivity() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goToStartActivity() {
        finish()

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}
