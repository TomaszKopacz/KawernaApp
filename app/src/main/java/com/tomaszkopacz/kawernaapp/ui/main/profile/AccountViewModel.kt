package com.tomaszkopacz.kawernaapp.ui.main.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaszkopacz.kawernaapp.data.Message
import com.tomaszkopacz.kawernaapp.data.Result
import com.tomaszkopacz.kawernaapp.managers.RestorePasswordService
import com.tomaszkopacz.kawernaapp.qr.QRGenerator
import com.tomaszkopacz.kawernaapp.managers.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val userManager: UserManager,
    private val restorePasswordService: RestorePasswordService
) : ViewModel() {

    private val state = MutableLiveData<String>()

    private var _qrCode: Bitmap? = null
    private var qrCode = MutableLiveData<Bitmap>()

    init {
        generateQRCode()
        exposeQRCodeBitmap()
    }

    fun getState(): LiveData<String> = state

    fun getQRCode(): LiveData<Bitmap> = qrCode

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

    fun changePassword(password: String) {
        if (password.isEmpty()) {
            state.postValue(Message.EMPTY_DATA)

        } else {
            updatePassword(password)
        }
    }

    private fun updatePassword(password: String) {
        GlobalScope.launch {
            when (val result =
                restorePasswordService.updatePassword(
                    userManager.getLoggedUser()!!.email,
                    password
                )) {

                is Result.Success -> {
                    state.postValue(Message.PASSWORD_UPDATED)
                }

                is Result.Failure -> {
                    state.postValue(result.message.text)
                }
            }
        }
    }
}