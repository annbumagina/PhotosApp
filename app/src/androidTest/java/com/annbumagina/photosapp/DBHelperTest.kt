package com.annbumagina.photosapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DBHelperTest {
    private val NUM = 10

    private lateinit var dbHelper: DBHelper
    private lateinit var title: ArrayList<String>
    private lateinit var links: ArrayList<String>
    private lateinit var ids: ArrayList<Int>
    private lateinit var description: ArrayList<String>

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        dbHelper = DBHelper(appContext)
        title = ArrayList()
        links = ArrayList()
        ids = ArrayList()
        description = ArrayList()
    }

    @After
    fun tearDown() {
        dbHelper.deleteTable()
    }

    @Test
    fun testEmpty() {
        dbHelper.restoreData(title, links, description, ids)

        assertEquals(0, title.size)
        assertEquals(0, links.size)
        assertEquals(0, description.size)
    }

    @Test
    fun testAdd() {
        for (i in 0 until NUM) {
            dbHelper.addElement("$i", "hello $i", "https://$i")
        }
        dbHelper.restoreData(title, links, description, ids)

        assertEquals(NUM, title.size)
        assertEquals(NUM, links.size)
        assertEquals(NUM, description.size)
        for (i in 0 until NUM) {
            assertEquals("$i", title[i])
            assertEquals("https://$i", links[i])
            assertEquals("hello $i", description[i])
        }
    }

    @Test
    fun testRemove() {
        for (i in 0 until NUM) {
            dbHelper.addElement("$i", "hello $i", "https://$i")
        }
        dbHelper.restoreData(title, links, description, ids)
        assertEquals(NUM, ids.size)
        for (i in ids.indices step 2) {
            dbHelper.deleteElement(ids[i])
        }
        dbHelper.restoreData(title, links, description, ids)


        assertEquals(NUM/2, title.size)
        assertEquals(NUM/2, links.size)
        assertEquals(NUM/2, description.size)
        for (i in 0 until NUM/2) {
            val name = i * 2 + 1
            assertEquals("$name", title[i])
            assertEquals("https://$name", links[i])
            assertEquals("hello $name", description[i])
        }
    }
}