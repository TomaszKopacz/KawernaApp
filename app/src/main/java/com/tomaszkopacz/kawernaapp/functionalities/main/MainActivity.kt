package com.tomaszkopacz.kawernaapp.functionalities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.di.MainComponent
import com.tomaszkopacz.kawernaapp.functionalities.game.GameActivity
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.container)
        navigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signout -> {
                viewModel.logout()
                navigateToStartActivity()
                true
            }
            else -> {
                true
            }
        }
    }

    private fun navigateToStartActivity() {
        finish()

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    fun navigateToNewGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
