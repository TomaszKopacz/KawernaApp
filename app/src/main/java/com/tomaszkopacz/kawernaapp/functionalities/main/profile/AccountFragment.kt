package com.tomaszkopacz.kawernaapp.functionalities.main.profile

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
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
