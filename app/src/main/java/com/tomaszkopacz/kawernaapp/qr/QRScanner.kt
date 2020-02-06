package com.tomaszkopacz.kawernaapp.qr

import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class QRScanner(
    private val view: DecoratedBarcodeView,
    callback: BarcodeCallback) {

    init {
        view.setStatusText("")
        view.decodeContinuous(callback)
    }

    fun resume() {
        if (!view.isActivated)
            view.resume()
    }

    fun pause() {
        view.pause()
    }
}