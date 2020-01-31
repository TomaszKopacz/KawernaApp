package com.tomaszkopacz.kawernaapp.functionalities.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.activities.StartActivity
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.FireStoreRepository
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_login, container, false)

        viewModel = ViewModelProviders
            .of(this, ViewModelFactory(
                AuthManager(),
                SharedPrefsRepository.getInstance(context!!),
                FireStoreRepository()))
            .get(LoginViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeToUI()
        subscribeToViewModel()
    }

    private fun subscribeToUI() {
        setSubmitButtonListener()
    }

    private fun setSubmitButtonListener() {
        loginSubmit.setOnClickListener {
            loginAttempt()
        }
    }

    private fun loginAttempt() {
        val email = loginMail.text.toString()
        val password = loginPassword.text.toString()
        viewModel.loginAttempt(email, password)
    }

    private fun subscribeToViewModel() {
        setStateObserver()
    }

    private fun setStateObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                LoginViewModel.STATE_LOGIN_SUCCEED -> goToMainActivity()
                LoginViewModel.STATE_LOGIN_FAILED -> showErrorMessage()
            }
        })
    }

    private fun goToMainActivity() {
        (activity as StartActivity).navigateToMainActivity()
    }

    private fun showErrorMessage() {
        Toast.makeText(context, "Login failed!", Toast.LENGTH_LONG).show()
    }
}
