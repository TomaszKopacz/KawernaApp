package com.tomaszkopacz.kawernaapp.functionalities.start.register


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import com.tomaszkopacz.kawernaapp.authentication.AuthManager
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_signup, container, false)

        viewModel = ViewModelProviders
            .of(this, ViewModelFactory(
                AuthManager(),
                SharedPrefsRepository.getInstance(context!!),
                FireStoreRepository()
            ))
            .get(SignUpViewModel::class.java)

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
        signUpSubmit.setOnClickListener {
            val mail = signUpMail.text.toString()
            val name = signUpName.text.toString()
            val password = signUpPassword.text.toString()

            viewModel.register(mail, name, password)
        }
    }

    private fun subscribeToViewModel() {
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                SignUpViewModel.STATE_REGISTRATION_SUCCEED -> goToMainActivity()
                SignUpViewModel.STATE_REGISTRATION_FAILED -> showMessage("Registration failed!")
            }
        })
    }

    private fun goToMainActivity() {
        (activity as StartActivity).navigateToMainActivity()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
