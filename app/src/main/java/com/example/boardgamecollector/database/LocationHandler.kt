package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.Location

class LocationHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertLocation(location: Location) {
        val contentValues = ContentValues()
        contentValues.put(L_COLUMN_NAME, location.name)

        db.insert(TABLE_LOCATIONS, null, contentValues)
    }

    fun getAllLocations(): List<Location> {
        val locations = mutableListOf<Location>()

        val query = "SELECT * FROM $TABLE_LOCATIONS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(L_COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(L_COLUMN_NAME))

                val location = Location(id, name)
                locations.add(location)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return locations
    }

    fun updateLocation(location: Location) {
        val contentValues = ContentValues()
        contentValues.put(L_COLUMN_NAME, location.name)

        db.update(TABLE_BOARD_GAMES, contentValues, "$L_COLUMN_ID = ?",
            arrayOf(location.id.toString()))
    }

    fun deleteLocation(id: Int) {
        val success = db.delete(TABLE_LOCATIONS, "$L_COLUMN_ID= ?",
            arrayOf(id.toString()))
    }
}