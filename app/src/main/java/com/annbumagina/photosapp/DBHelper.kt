package com.annbumagina.photosapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {
    private val tableName = "mytable"
    private val idColumn = "id"
    private val titleColumn = "title"
    private val linkColumn = "link"
    private val descColumn = "desc"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table $tableName (" +
                    "$idColumn integer primary key autoincrement, " +
                    "$titleColumn text, " +
                    "$linkColumn text, " +
                    "$descColumn text);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun deleteElement(id: Int) {
        writableDatabase.delete(tableName, "id = $id", null)
        close()
    }

    fun deleteTable() {
        writableDatabase.execSQL("DELETE FROM $tableName")
        close()
    }

    fun addElement(title: String?, desc: String?, link: String?) {
        val cv = ContentValues()
        cv.put(titleColumn, title)
        cv.put(descColumn, desc)
        cv.put(linkColumn, link)
        writableDatabase.insert(tableName, null, cv)
        close()
    }

    fun restoreData(
        title: ArrayList<String>,
        links: ArrayList<String>,
        description: ArrayList<String>,
        ids: ArrayList<Int>
    ) {
        val c = writableDatabase.query("mytable", null, null, null, null, null, null)
        title.clear()
        links.clear()
        description.clear()
        ids.clear()
        if (c.moveToFirst()) {
            val idColIndex = c.getColumnIndex(idColumn)
            val titleColIndex = c.getColumnIndex(titleColumn)
            val linkColIndex = c.getColumnIndex(linkColumn)
            val descColIndex = c.getColumnIndex(descColumn)
            do {
                title.add(c.getString(titleColIndex))
                links.add(c.getString(linkColIndex))
                description.add(c.getString(descColIndex))
                ids.add(c.getInt(idColIndex))
            } while (c.moveToNext())
        }
        c.close()
        close()
    }
}