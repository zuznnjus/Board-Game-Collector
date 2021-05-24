package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.Designer

class DesignerHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertDesigner(name: String) {
        val contentValues = ContentValues()
        contentValues.put(D_COLUMN_NAME, name)
        db.insert(TABLE_DESIGNERS, null, contentValues)
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