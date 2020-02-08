package com.tomaszkopacz.kawernaapp.extensions

import android.widget.EditText

fun EditText.setCursorToEnd() {
    this.setSelection(this.text.length)
}