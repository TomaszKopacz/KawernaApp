package com.tomaszkopacz.kawernaapp.functionalities.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.di.SplashComponent
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    lateinit var splashComponent: SplashComponent

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        splashComponent = (application as MyApplication).appComponent.splashComponent().create()
        splashComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                SplashViewModel.STATE_USER_LOGGED_IN -> goToMainActivity()
                SplashViewModel.STATE_NO_USER_LOGGED_IN -> goToStartActivity()
            }
        })
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
