package com.tomaszkopacz.kawernaapp.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.Button
import com.tomaszkopacz.kawernaapp.R

object TermsDialog {
    private var dialog: Dialog? = null

    fun show(context: Context, listener: AcceptListener) {
        dialog = Dialog(context)
        dialog?.let { dialog ->
            dialog.setContentView(R.layout.dialog_policy_privacy)
            val acceptButton = dialog.findViewById<Button>(R.id.accept_button)
            acceptButton.setOnClickListener { listener.onAccept() }
            dialog.show()
        }
    }

    fun hide() {
        dialog?.dismiss()
    }

    interface AcceptListener {
        fun onAccept()
    }
}