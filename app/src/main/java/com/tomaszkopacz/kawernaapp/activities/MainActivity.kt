package com.tomaszkopacz.kawernaapp.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.auth.AuthManager
import com.tomaszkopacz.kawernaapp.data.Languages
import com.tomaszkopacz.kawernaapp.dialogs.DialogProvider
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
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.language -> showLanguageDialog()
            R.id.signout -> signOut()
            else -> {
                true
            }
        }
    }

    fun navigateToStartActivity() {
        finish()

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    fun navigateToNewGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun showLanguageDialog() : Boolean {
        DialogProvider.createLanguageDialog(this, languageChosenListener).show()
        return true
    }

    private val languageChosenListener = object : DialogProvider.ChosenLanguageListener {
        override fun onLanguageChosen(language: Languages, dialog: Dialog) {
            setLanguage(language.code)
            dialog.dismiss()
        }

    }

    private fun setLanguage(lang: String) {
        val localeManager = LocaleManager()
        localeManager.changeLanguage(this, lang)
    }

    private fun signOut(): Boolean {
        AuthManager().logoutUser()
        navigateToStartActivity()

        return true
    }
}
