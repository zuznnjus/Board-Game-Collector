package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.Location

class LocationHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertLocation(locationName: String) {
        val contentValues = ContentValues()
        contentValues.put(L_COLUMN_NAME, locationName)

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

    fun getLocationName(locationId: Int): String {
        val query = "SELECT * FROM $TABLE_LOCATIONS WHERE $L_COLUMN_ID=?"
        val cursor = db.rawQuery(query, arrayOf(locationId.toString()))

        lateinit var locationName: String

        if (cursor.moveToFirst()) {
            locationName = cursor.getString(cursor.getColumnIndex(L_COLUMN_NAME))
        }

        cursor.close()
        return locationName
    }

    fun updateLocation(locationId: Int, locationName: String) {
        val contentValues = ContentValues()
        contentValues.put(L_COLUMN_NAME, locationName)

        db.update(
            TABLE_LOCATIONS, contentValues, "$L_COLUMN_ID = ?",
            arrayOf(locationId.toString()))
    }

    fun deleteLocation(id: Int) {
        db.delete(TABLE_LOCATIONS, "$L_COLUMN_ID= ?",
            arrayOf(id.toString()))
    }
}