package com.tomaszkopacz.kawernaapp.util.extensions

import android.widget.EditText

fun EditText.setCursorToEnd() {
    this.setSelection(this.text.length)
}