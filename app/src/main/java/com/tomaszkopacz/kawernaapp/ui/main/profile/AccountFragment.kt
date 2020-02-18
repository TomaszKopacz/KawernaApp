package com.tomaszkopacz.kawernaapp.ui.main.profile

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

class AccountFragment : Fragment() {

    private lateinit var layout: View

    @Inject
    lateinit var viewModel: AccountViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_account, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeToViewModel()
        subscribeToUI()
    }

    private fun subscribeToViewModel() {
        observeState()
        observeQRCode()
    }

    private fun observeState() {
        viewModel.getState().observe(this, Observer { state ->
            when (state) {
                Message.PASSWORD_UPDATED -> {
                    exp_change_password.collapse()
                }

                else -> {
                    showErrorMessage(state)
                }
            }
        })
    }

    private fun observeQRCode() {
        viewModel.getQRCode().observe(this, Observer { bitmap ->
            displayQRCode(bitmap)
        })
    }

    private fun displayQRCode(bitmap: Bitmap) {
        QRCode.setImageBitmap(bitmap)
    }

    private fun subscribeToUI() {
        setExpandableListener()
        setChangePasswordButtonListener()
    }

    private fun setExpandableListener() {
        tv_change_password.setOnClickListener {

            if (exp_change_password.isExpanded) {
                exp_change_password.collapse()

            } else {
                exp_change_password.expand()
            }
        }
    }

    private fun setChangePasswordButtonListener() {
        btn_update_password.setOnClickListener {
            viewModel.changePassword(et_new_password.text.toString())
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
