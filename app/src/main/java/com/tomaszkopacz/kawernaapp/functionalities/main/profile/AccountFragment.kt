package com.tomaszkopacz.kawernaapp.functionalities.main.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.authentication.AuthManager
import com.tomaszkopacz.kawernaapp.database.FireStoreRepository
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: AccountViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_account, container, false)

        viewModel = ViewModelProviders
            .of(
                this, ViewModelFactory(
                    AuthManager(),
                    SharedPrefsRepository.getInstance(context!!),
                    FireStoreRepository()
                )
            )
            .get(AccountViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.qrCode.observe(this, Observer { bitmap ->
            displayQRCode(bitmap)
        })
    }

    private fun displayQRCode(bitmap: Bitmap) {
        QRCode.setImageBitmap(bitmap)
    }
}
