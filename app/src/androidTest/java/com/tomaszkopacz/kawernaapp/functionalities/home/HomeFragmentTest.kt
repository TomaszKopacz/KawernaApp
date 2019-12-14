package com.tomaszkopacz.kawernaapp.functionalities.home

import androidx.test.rule.ActivityTestRule
import com.tomaszkopacz.kawernaapp.activities.MainActivity
import org.junit.Before
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class HomeFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun test(){

    }
}