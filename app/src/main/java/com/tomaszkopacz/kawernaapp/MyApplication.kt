package com.tomaszkopacz.kawernaapp

import android.app.Application
import com.tomaszkopacz.kawernaapp.di.AppComponent
import com.tomaszkopacz.kawernaapp.di.DaggerAppComponent

open class MyApplication : Application(){

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}