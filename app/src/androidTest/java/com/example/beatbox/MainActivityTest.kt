package com.example.beatbox

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityMainRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun showsFirstFileName() {
        Espresso.onView(ViewMatchers.withText("cjipie")).check(matches(isDisplayed()))
    }
}