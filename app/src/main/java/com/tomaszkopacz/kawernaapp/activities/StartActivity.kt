package com.tomaszkopacz.kawernaapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.tomaszkopacz.kawernaapp.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val navController = Navigation.findNavController(this, R.id.start_container)
        start_bottom_navigation.setupWithNavController(navController)
    }
}
