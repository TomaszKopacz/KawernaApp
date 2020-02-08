package com.tomaszkopacz.kawernaapp.dialogs

import android.app.Dialog
import android.content.Context
import com.tomaszkopacz.kawernaapp.R

object ProgressDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        dialog = Dialog(context)
        dialog?.let { dialog ->
            dialog.setContentView(R.layout.dialog_progress_bar)
            dialog.show()
        }
    }

    fun hide() {
        dialog?.dismiss()
    }
}