package com.tomaszkopacz.kawernaapp.functionalities.main.profile

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.qr.QRGenerator
import com.tomaszkopacz.kawernaapp.user.UserManager

class AccountViewModel(
    private val userManager: UserManager
) : ViewModel() {

    private var _qrCode: Bitmap? = null
    var qrCode = MutableLiveData<Bitmap>()

    init {
        generateQRCode()
        exposeQRCodeBitmap()
    }

    private fun generateQRCode() {
        val qrCode = QRGenerator.generateQRCode(getStringToEncode())
        this._qrCode = qrCode
    }

    private fun getStringToEncode(): String {
        return userManager.getLoggedUser()!!.email
    }

    private fun exposeQRCodeBitmap() {
        qrCode.postValue(_qrCode)
    }
}