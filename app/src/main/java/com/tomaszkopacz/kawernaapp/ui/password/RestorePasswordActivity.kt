package com.tomaszkopacz.kawernaapp.ui.password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.di.RestorePasswordComponent
import com.tomaszkopacz.kawernaapp.ui.main.MainActivity
import com.tomaszkopacz.kawernaapp.ui.start.StartActivity
import kotlinx.android.synthetic.main.activity_restore_password.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RestorePasswordActivity : AppCompatActivity() {

    private lateinit var restorePasswordComponent: RestorePasswordComponent

    @Inject
    lateinit var viewModel: RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        restorePasswordComponent =
            (application as MyApplication).appComponent.restorePasswordComponent().create()
        restorePasswordComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)

        subscribeToViewModel()
        subscribeToUI()
    }

    private fun subscribeToViewModel() {
        observeState()
    }

    private fun observeState() {
        viewModel.getState().observe(this, Observer { state ->
            when (state) {
                Message.PASSWORD_RESTORED -> {

                }
            }
        })
    }

    private fun subscribeToUI() {
        btn_restore_password.setOnClickListener {
            viewModel.restorePassword(et_email.text.toString())
        }
    }

    private fun goToStartActivity() {
        finish()

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}
