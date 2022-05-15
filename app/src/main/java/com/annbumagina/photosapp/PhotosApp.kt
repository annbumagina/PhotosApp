package com.annbumagina.photosapp

import android.app.Application

class PhotosApp: Application() {
    lateinit var dbHelper: DBHelper

    override fun onCreate() {
        super.onCreate()
        dbHelper = DBHelper(applicationContext)
    }
}