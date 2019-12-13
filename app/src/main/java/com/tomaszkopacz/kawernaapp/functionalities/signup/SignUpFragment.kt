package com.tomaszkopacz.kawernaapp.functionalities.signup


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.activities.StartActivity
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
        val user = AuthManager().getLoggedUser()
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

        AuthManager().registerUser(email, password, registrationListener)
    }

    private val registrationListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) {
            goToMainActivity()
        }

        override fun onFailure(exception: Exception) {
            Log.d("Kawerna", "Registration failed")
        }
    }

    private fun goToMainActivity() {
        (activity as StartActivity).navigateToMainActivity()
    }
}
