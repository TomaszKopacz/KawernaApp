package com.tomaszkopacz.kawernaapp.functionalities.start.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomaszkopacz.kawernaapp.MyApplication
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import com.tomaszkopacz.kawernaapp.authentication.AuthManager
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as StartActivity).startComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_login, container, false)

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
        viewModel.login(email, password)
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
