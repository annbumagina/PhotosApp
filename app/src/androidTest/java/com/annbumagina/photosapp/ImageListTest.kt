package com.annbumagina.photosapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImageListTest {

    @Test
    fun testListItems() {
        val photos = Photos()
        val item1 = Photos.Items()
        val item2 = Photos.Items()
        item1.tags = "hello"
        item2.tags = "world"
        photos.hits = arrayOf(item1, item2)

        val scenario = launchFragmentInContainer<ImageList>()
        with(scenario) {
            onFragment { fragment ->
                fragment.setJSON(photos)
            }
        }

        onView(withId(R.id.recyclerView))
            .check(matches(hasDescendant(withText("hello"))))
        onView(withId(R.id.recyclerView))
            .check(matches(hasDescendant(withText("world"))))
    }
}