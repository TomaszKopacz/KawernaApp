package com.tomaszkopacz.kawernaapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.tomaszkopacz.kawernaapp.functionalities.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExampleInstrumentedTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun someTest() {
        for(i in 1..100)
            onView(withId(R.id.container)).perform(ViewActions.click())
    }
}
