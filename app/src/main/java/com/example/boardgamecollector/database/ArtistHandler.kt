package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.Artist

class ArtistHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertArtist(artist: Artist): Int {
        val query = "SELECT * FROM $TABLE_ARTISTS WHERE $A_COLUMN_NAME=?"
        val cursor = db.rawQuery(query, arrayOf(artist.name))
        if (cursor.moveToFirst()) {
            val value = cursor.getInt(cursor.getColumnIndex(A_COLUMN_ID))
            cursor.close()
            return value
        }
        cursor.close()

        val contentValues = ContentValues()
        contentValues.put(A_COLUMN_NAME, artist.name)
        return db.insert(TABLE_ARTISTS, null, contentValues).toInt()
    }

    fun getAllArtists(): List<Artist> {
        val artists = mutableListOf<Artist>()

        val query = "SELECT * FROM $TABLE_ARTISTS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(A_COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(A_COLUMN_NAME))

                val artist = Artist(id, name)
                artists.add(artist)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return artists
    }
}