package com.tomaszkopacz.kawernaapp.functionalities.account

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.tomaszkopacz.kawernaapp.sharedprefs.SharedPrefsRepository

class AccountViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository
) : ViewModel() {

    var qrCodeBitmap = MutableLiveData<Bitmap>()

    init {
        generateQRCode()
    }

    private fun generateQRCode() {
        val data: String? = getStringToEncode()
        val writer = MultiFormatWriter()

        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(bitMatrix)

            exposeQRCodeBitmap(bitmap)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStringToEncode(): String? {
        return sharedPrefsRepository.getLoggedUser()!!.email
    }

    private fun exposeQRCodeBitmap(bitmap: Bitmap) {
        qrCodeBitmap.postValue(bitmap)
    }
}