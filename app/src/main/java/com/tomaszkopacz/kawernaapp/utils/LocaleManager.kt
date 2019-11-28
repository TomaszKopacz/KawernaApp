package com.tomaszkopacz.kawernaapp.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LocaleManager {

    fun changeLanguage(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale

        context.resources.configuration.updateFrom(config)
    }
}