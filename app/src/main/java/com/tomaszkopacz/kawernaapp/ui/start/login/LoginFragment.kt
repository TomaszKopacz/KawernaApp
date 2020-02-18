package com.tomaszkopacz.kawernaapp.ui.start.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.ui.dialogs.ProgressDialog
import com.tomaszkopacz.kawernaapp.ui.start.StartActivity
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
        setRestorePasswordButtonListener()
    }

    private fun setSubmitButtonListener() {
        loginSubmit.setOnClickListener {
            loginAttempt()
        }
    }

    private fun setRestorePasswordButtonListener() {
        btnRestorePassword.setOnClickListener {
            goToRestorePasswordActivity()
        }
    }

    private fun loginAttempt() {
        showProgressBar()

        val email = loginMail.text.toString()
        val password = loginPassword.text.toString()
        viewModel.login(email, password)
    }

    private fun subscribeToViewModel() {
        setStateObserver()
    }

    private fun setStateObserver() {
        viewModel.state.observe(this, Observer { state ->
            hideProgressBar()
            when (state) {
                Message.LOGIN_SUCCEED -> goToMainActivity()
                else -> showErrorMessage(state)
            }
        })
    }

    private fun goToMainActivity() {
        (activity as StartActivity).navigateToMainActivity()
    }

    private fun goToRestorePasswordActivity() {
        (activity as StartActivity).navigateToRestorePasswordActivity()
    }

    private fun showProgressBar() {
        ProgressDialog.show(context!!)
    }

    private fun hideProgressBar() {
        ProgressDialog.hide()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
