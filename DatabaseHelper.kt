package com.example.brahimmasmoudi.sqllite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,"MyDatabase", null, 1) {

    val TABLE_NAME = "pup_table"
    val COL_1 = "ID"
    val COL_2 = "SCENARIO"
    val COL_3 = "DOCUMENT_TYPE"
    val COL_4 = "CONNECTOR"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,SCENARIO TEXT,DOCUMENT_TYPE TEXT,CONNECTOR TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    fun insertData(scenario: String, documentType: String, connector: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, scenario)
        contentValues.put(COL_3, documentType)
        contentValues.put(COL_4, connector)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result.toInt() != -1
    }

    fun getAllData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("select * from $TABLE_NAME", null)
    }

    fun updateData(id: String, name: String, surname: String, marks: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id: String): Int? {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }
}