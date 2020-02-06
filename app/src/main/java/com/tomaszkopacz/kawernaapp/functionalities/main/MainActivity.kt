package com.tomaszkopacz.kawernaapp.functionalities.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.tomaszkopacz.kawernaapp.R
import com.tomaszkopacz.kawernaapp.authentication.AuthManager
import com.tomaszkopacz.kawernaapp.functionalities.game.GameActivity
import com.tomaszkopacz.kawernaapp.functionalities.start.StartActivity
import com.tomaszkopacz.kawernaapp.utils.locale.Language
import com.tomaszkopacz.kawernaapp.storage.SharedPrefsRepository
import com.tomaszkopacz.kawernaapp.utils.locale.LocaleManager
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
        LanguageDialog.new(
            this,
            languageChosenListener
        ).show()
        return true
    }

    private val languageChosenListener = object :
        LanguageDialog.ChosenLanguageListener {
        override fun onLanguageChosen(language: Language, dialog: Dialog) {
            setAppLanguage(language.code)
            dialog.dismiss()
        }
    }

    private fun setAppLanguage(lang: String) {
        LocaleManager.changeLanguage(baseContext, lang)
        refreshContext()
    }

    private fun refreshContext() {
        val refreshIntent = Intent(this, MainActivity::class.java)

        finish()
        startActivity(refreshIntent)
    }

    private fun signOut(): Boolean {
        AuthManager().logoutUser()
        SharedPrefsRepository.getInstance(this).clearLoggedUser()
        navigateToStartActivity()

        return true
    }
}
