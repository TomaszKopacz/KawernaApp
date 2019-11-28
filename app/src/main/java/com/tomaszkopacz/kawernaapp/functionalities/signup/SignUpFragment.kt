package com.tomaszkopacz.kawernaapp.functionalities.signup


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_signup, container, false)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkLoggedUser()
        setListeners()
    }

    private fun checkLoggedUser() {
        val user = AuthManager.getLoggedUser()
        if (user == null) Log.d("Kawerna", "NO USER!")
        else goToMainActivity()
    }

    private fun setListeners() {
        setSubmitButtonListener()
    }

    private fun setSubmitButtonListener() {
        signUpSubmit.setOnClickListener {
            registerAttempt()
        }
    }

    private fun registerAttempt() {
        val email = signUpMail.text.toString()
        val password = signUpPassword.text.toString()

        AuthManager.registerUser(email, password, registrationListener)
    }

    private val registrationListener = object : AuthManager.AuthListener {
        override fun onSuccess() {
            goToMainActivity()
        }

        override fun onFailure() {
            Log.d("Kawerna", "Registration failed")
        }

    }

    private fun goToMainActivity() {
        val direction = SignUpFragmentDirections.actionSignupToMain()
        activity?.finish()
        findNavController().navigate(direction)
    }
}
