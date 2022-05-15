package com.annbumagina.photosapp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageDetailFragmentTest {
    @Test
    fun testAddButton() {
        val fragmentArgs = bundleOf(
            EXTRA_DESC to "hello",
            EXTRA_IDS to -1,
            EXTRA_TITLE to "hello",
            EXTRA_URL to "http://hello"
        )
        launchFragmentInContainer<ImageDetailFragment>(fragmentArgs)
        onView(withId(R.id.btn))
            .check(matches(withText("Add")))
    }

    @Test
    fun testRemoveButton() {
        val fragmentArgs = bundleOf(
            EXTRA_DESC to "hello",
            EXTRA_IDS to 1,
            EXTRA_TITLE to "hello",
            EXTRA_URL to "http://hello"
        )
        launchFragmentInContainer<ImageDetailFragment>(fragmentArgs)
        onView(withId(R.id.btn))
            .check(matches(withText("Remove")))
    }
}