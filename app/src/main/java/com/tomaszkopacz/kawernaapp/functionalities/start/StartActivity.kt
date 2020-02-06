package com.tomaszkopacz.kawernaapp.functionalities.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.di.StartComponent
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    lateinit var startComponent: StartComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        startComponent = (application as MyApplication).appComponent.startComponent().create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val navController = Navigation.findNavController(this, R.id.start_container)
        start_bottom_navigation.setupWithNavController(navController)
    }

    fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}
