package com.tomaszkopacz.kawernaapp.functionalities.game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.database.DataBaseRepository
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import com.tomaszkopacz.kawernaapp.game.GameManager
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.user.UserManager

class GameActivity : AppCompatActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {

        val db = FireStoreRepository()
        val sp = SharedPrefsRepository.getInstance(this)
        gameManager = GameManager(db)
        userManager = UserManager(db, sp)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    fun goToMainActivity() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun getUserManager(): UserManager {
        return userManager
    }

    fun getGameManager(): GameManager {
        return gameManager
    }
}
