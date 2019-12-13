package com.tomaszkopacz.kawernaapp.functionalities.account

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import kotlinx.android.synthetic.main.fragment_account.*
import java.lang.Exception

class AccountFragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_account, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayQRCode()
    }

    private fun displayQRCode() {
        val data: String? = getStringToEncode()
        val writer = MultiFormatWriter()

        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(bitMatrix)

            QRCode.setImageBitmap(bitmap)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStringToEncode(): String? {
        return AuthManager().getLoggedUser()
    }
}
