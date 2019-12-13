package com.tomaszkopacz.kawernaapp.functionalities.login


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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_login, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        setSubmitButtonListener()
    }

    private fun setSubmitButtonListener() {
        loginSubmit.setOnClickListener {
            loginAttempt()
        }
    }

    private fun loginAttempt() {
        val email = loginMail.text.toString().trim()
        val password = loginPassword.text.toString().trim()

        AuthManager().loginUser(email, password, loginListener)
    }

    private val loginListener = object : AuthManager.AuthListener {
        override fun onSuccess(user: FirebaseUser) { goToMainActivity() }

        override fun onFailure(exception: Exception) {
            Log.d("Kawerna", "Login failed")
        }
    }

    private fun goToMainActivity() {
        (activity as StartActivity).navigateToMainActivity()
    }
}
