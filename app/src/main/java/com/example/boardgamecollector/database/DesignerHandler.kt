package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.Designer

class DesignerHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertDesigner(name: String): Int {
        val query = "SELECT * FROM $TABLE_DESIGNERS WHERE $D_COLUMN_NAME=?"
        val cursor = db.rawQuery(query, arrayOf(name))
        if (cursor.moveToFirst()) {
            val value = cursor.getInt(cursor.getColumnIndex(D_COLUMN_ID))
            cursor.close()
            return value
        }
        cursor.close()

        val contentValues = ContentValues()
        contentValues.put(D_COLUMN_NAME, name)
        return db.insert(TABLE_DESIGNERS, null, contentValues).toInt()
    }

    fun getAllDesigners(): List<Designer> {
        val designers = mutableListOf<Designer>()

        val query = "SELECT * FROM $TABLE_DESIGNERS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(D_COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(D_COLUMN_NAME))

                val designer = Designer(id, name)
                designers.add(designer)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return designers
    }
}