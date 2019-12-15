package com.tomaszkopacz.kawernaapp.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.data.Languages

object DialogProvider {

    fun createLanguageDialog(context: Context, listener: ChosenLanguageListener) : Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_language)

        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radio_group)

        populateRadioGroupWithLanguages(context,radioGroup)

        radioGroup.setOnCheckedChangeListener { butGroup, checkedId ->
            val childCount = butGroup.childCount
            for (i in 0 until childCount) {
                val btn = butGroup.getChildAt(i) as RadioButton
                if (btn.id == checkedId) {
                    listener.onLanguageChosen(Languages.values()[i], dialog)
                }
            }
        }

        return dialog
    }

    private fun populateRadioGroupWithLanguages(
        context: Context,
        radioGroup: RadioGroup
    ) {
        for (lang in Languages.values()) {
            val radioBtn = RadioButton(context)
            radioBtn.text = lang.text
            radioGroup.addView(radioBtn)
        }
    }

    interface ChosenLanguageListener {
        fun onLanguageChosen(language: Languages, dialog: Dialog)
    }
}