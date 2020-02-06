package com.tomaszkopacz.kawernaapp.utils.extensions

import android.widget.EditText

fun EditText.setCursorToEnd() {
    this.setSelection(this.text.length)
}