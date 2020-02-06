package com.tomaszkopacz.kawernaapp.qr

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

object QRGenerator {

    fun generateQRCode(data: String): Bitmap? {
        val writer = MultiFormatWriter()

        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200)
            val encoder = BarcodeEncoder()

            return encoder.createBitmap(bitMatrix)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}