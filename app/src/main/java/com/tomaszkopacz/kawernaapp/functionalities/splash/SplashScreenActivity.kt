package com.tomaszkopacz.kawernaapp.functionalities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.user.UserManager

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        process()
    }

    private fun process() {
        if (isAnyUserLoggedIn()) {
            goToMainActivity()
        }

        else {
            goToStartActivity()
        }
    }

    private fun isAnyUserLoggedIn() : Boolean {
        val repository = FireStoreRepository()
        val storage = SharedPrefsRepository.getInstance(this)
        val userManager = UserManager(repository, storage)

        return userManager.isUserLoggedIn()
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
