package com.tomaszkopacz.kawernaapp.functionalities.start.register


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
import com.tomaszkopacz.kawernaapp.dialogs.ProgressDialog
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: SignUpViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as StartActivity).startComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layout = inflater.inflate(R.layout.fragment_signup, container, false)

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
            showProgressBar()

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
            hideProgressBar()

            when (state) {
                Message.REGISTRATION_SUCCEED -> goToMainActivity()
                else -> showErrorMessage(state)
            }
        })
    }

    private fun goToMainActivity() {
        (activity as StartActivity).navigateToMainActivity()
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
