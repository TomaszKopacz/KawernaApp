package com.tomaszkopacz.kawernaapp.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.utils.LocaleManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Kawerna"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.container)
        navigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options -> showLanguageDialog()
            else -> {true}
        }
    }

    private fun showLanguageDialog(): Boolean {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_language)

        val langList = arrayListOf("Polski", "English")
        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radio_group)

        for (lang in langList) {
            val radioBtn = RadioButton(this)
            radioBtn.text = lang
            radioGroup.addView(radioBtn)
        }

        radioGroup.setOnCheckedChangeListener { butGroup, checkedId ->
            val childCount = butGroup.childCount
            for (i in 0 until childCount) {
                val btn = butGroup.getChildAt(i) as RadioButton
                if (btn.id == checkedId) {
                    setLanguage()
                    dialog.dismiss()
                }
            }
        }

        dialog.show()

        return true
    }

    private fun setLanguage() {
        val localeManager = LocaleManager()
        localeManager.changeLanguage(this, "pl")
    }
}
