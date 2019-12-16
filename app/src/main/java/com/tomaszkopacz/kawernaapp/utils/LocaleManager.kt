package com.tomaszkopacz.kawernaapp.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LocaleManager {

    fun changeLanguage(baseContext: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}