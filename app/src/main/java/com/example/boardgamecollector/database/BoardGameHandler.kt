package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.BoardGame

class BoardGameHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertBoardGame(boardGame: BoardGame): Int {
        val contentValues = ContentValues()

        contentValues.put(BG_COLUMN_BGG_ID, boardGame.bggId)
        contentValues.put(BG_COLUMN_TITLE, boardGame.title)
        contentValues.put(BG_COLUMN_ORIGINAL_TITLE, boardGame.originalTitle)
        contentValues.put(BG_COLUMN_YEAR_PUBLISHED, boardGame.yearPublished)
        contentValues.put(BG_COLUMN_DESCRIPTION, boardGame.description)
        contentValues.put(BG_COLUMN_ORDER_DATE,
            boardGame.orderDate?.let { Converter.convertLocalDateToEpochDay(it) })
        contentValues.put(BG_COLUMN_ADDED_TO_COLLECTION_DATE,
            boardGame.addedToCollectionDate?.let { Converter.convertLocalDateToEpochDay(it) })
        contentValues.put(BG_COLUMN_PRICE, boardGame.price)
        contentValues.put(BG_COLUMN_SUGGESTED_RETAIL_PRICE, boardGame.suggestedRetailPrice)
        contentValues.put(BG_COLUMN_EAN_OR_UPC, boardGame.eanOrUpc)
        contentValues.put(BG_COLUMN_PRODUCTION_CODE, boardGame.productionCode)
        contentValues.put(BG_COLUMN_TYPE, boardGame.type)
        contentValues.put(BG_COLUMN_COMMENT, boardGame.comment)
        contentValues.put(BG_COLUMN_THUMBNAIL, boardGame.thumbnail)

        return db.insert(TABLE_BOARD_GAMES, null, contentValues).toInt()
    }

    fun addDesigner(boardGameId: Int, designerId: Int) {
        val contentValues = ContentValues()
        contentValues.put(BG_D_COLUMN_GAME_ID, boardGameId)
        contentValues.put(BG_D_COLUMN_DESIGNER_ID, designerId)
        db.insert(TABLE_BOARD_GAMES_DESIGNERS, null, contentValues)
    }

    fun addArtist(boardGameId: Int, artistId: Int) {
        val contentValues = ContentValues()
        contentValues.put(BG_A_COLUMN_GAME_ID, boardGameId)
        contentValues.put(BG_A_COLUMN_ARTIST_ID, artistId)
        db.insert(TABLE_BOARD_GAMES_ARTISTS, null, contentValues)
    }

    fun setLocation(boardGameId: Int, locationId: Int?) {
        if (locationId == null) {
            db.delete(
                TABLE_BOARD_GAMES_LOCATIONS,
                "$BG_L_COLUMN_GAME_ID=?",
                arrayOf(boardGameId.toString())
            )
            return
        }

        val query = "SELECT * FROM $TABLE_BOARD_GAMES_LOCATIONS WHERE ${BG_L_COLUMN_GAME_ID}=?"
        val cursor = db.rawQuery(query, arrayOf(boardGameId.toString()))
        val exists = cursor.moveToFirst()
        cursor.close()

        val contentValues = ContentValues()
        contentValues.put(BG_L_COLUMN_GAME_ID, boardGameId);
        contentValues.put(BG_L_COLUMN_LOCATION_ID, locationId);

        if (exists) {
            db.update(
                TABLE_BOARD_GAMES_LOCATIONS,
                contentValues,
                "$BG_L_COLUMN_GAME_ID=?",
                arrayOf(boardGameId.toString())
            )
        } else {
            db.insert(TABLE_BOARD_GAMES_LOCATIONS, null, contentValues)
        }
    }

    fun getAllBoardGames(): List<BoardGame> {
        val boardGames = mutableListOf<BoardGame>()

        val query = "SELECT * FROM $TABLE_BOARD_GAMES"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(BG_COLUMN_ID))
                val bggId = cursor.getInt(cursor.getColumnIndex(BG_COLUMN_BGG_ID))
                val title = cursor.getString(cursor.getColumnIndex(BG_COLUMN_TITLE))
                val originalTitle =
                    cursor.getString(cursor.getColumnIndex(BG_COLUMN_ORIGINAL_TITLE))
                val yearPublished = cursor.getInt(cursor.getColumnIndex(BG_COLUMN_YEAR_PUBLISHED))
                val description = cursor.getString(cursor.getColumnIndex(BG_COLUMN_DESCRIPTION))
                val orderDate = Converter.convertEpochDayToLocalDate(
                    cursor.getLong(cursor.getColumnIndex(BG_COLUMN_ORDER_DATE))
                )
                val addedToCollectionDate = Converter.convertEpochDayToLocalDate(
                    cursor.getLong(cursor.getColumnIndex(BG_COLUMN_ADDED_TO_COLLECTION_DATE))
                )
                val price = cursor.getDouble(cursor.getColumnIndex(BG_COLUMN_PRICE))
                val suggestedRetailPrice = cursor.getDouble(
                    cursor.getColumnIndex(BG_COLUMN_SUGGESTED_RETAIL_PRICE)
                )
                val eanOrUpc = cursor.getString(cursor.getColumnIndex(BG_COLUMN_EAN_OR_UPC))
                val productionCode = cursor.getString(
                    cursor.getColumnIndex(BG_COLUMN_PRODUCTION_CODE)
                )
                val type = cursor.getString(cursor.getColumnIndex(BG_COLUMN_TYPE))
                val comment = cursor.getString(cursor.getColumnIndex(BG_COLUMN_COMMENT))
                val thumbnail = cursor.getString(cursor.getColumnIndex(BG_COLUMN_THUMBNAIL))

                val boardGame = BoardGame(
                    id, bggId, title, originalTitle, yearPublished,
                    description, orderDate, addedToCollectionDate, price, suggestedRetailPrice,
                    eanOrUpc, productionCode, type, comment, thumbnail
                )

                boardGames.add(boardGame)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return boardGames
    }

    fun getBoardGameById(boardGameId: Int): BoardGame {
        val query = "SELECT * FROM $TABLE_BOARD_GAMES WHERE $BG_COLUMN_ID=?"
        val cursor = db.rawQuery(query, arrayOf(boardGameId.toString()))
        lateinit var boardGame: BoardGame

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(BG_COLUMN_ID))
            val bggId = cursor.getInt(cursor.getColumnIndex(BG_COLUMN_BGG_ID))
            val title = cursor.getString(cursor.getColumnIndex(BG_COLUMN_TITLE))
            val originalTitle =
                cursor.getString(cursor.getColumnIndex(BG_COLUMN_ORIGINAL_TITLE))
            val yearPublished = cursor.getInt(cursor.getColumnIndex(BG_COLUMN_YEAR_PUBLISHED))
            val description = cursor.getString(cursor.getColumnIndex(BG_COLUMN_DESCRIPTION))
            val orderDate = Converter.convertEpochDayToLocalDate(
                cursor.getLong(cursor.getColumnIndex(BG_COLUMN_ORDER_DATE))
            )
            val addedToCollectionDate = Converter.convertEpochDayToLocalDate(
                cursor.getLong(cursor.getColumnIndex(BG_COLUMN_ADDED_TO_COLLECTION_DATE))
            )
            val price = cursor.getDouble(cursor.getColumnIndex(BG_COLUMN_PRICE))
            val suggestedRetailPrice = cursor.getDouble(
                cursor.getColumnIndex(BG_COLUMN_SUGGESTED_RETAIL_PRICE)
            )
            val eanOrUpc = cursor.getString(cursor.getColumnIndex(BG_COLUMN_EAN_OR_UPC))
            val productionCode = cursor.getString(
                cursor.getColumnIndex(BG_COLUMN_PRODUCTION_CODE)
            )
            val type = cursor.getString(cursor.getColumnIndex(BG_COLUMN_TYPE))
            val comment = cursor.getString(cursor.getColumnIndex(BG_COLUMN_COMMENT))
            val thumbnail = cursor.getString(cursor.getColumnIndex(BG_COLUMN_THUMBNAIL))

            boardGame = BoardGame(
                id, bggId, title, originalTitle, yearPublished,
                description, orderDate, addedToCollectionDate, price, suggestedRetailPrice,
                eanOrUpc, productionCode, type, comment, thumbnail
            )
        }

        cursor.close()
        return boardGame
    }

    fun getBoardGamesInLocation(locationId: Int): List<BoardGame> {
        val query = "SELECT * FROM $TABLE_BOARD_GAMES_LOCATIONS " +
                "WHERE ${BG_L_COLUMN_LOCATION_ID}=?"
        val cursor = db.rawQuery(query, arrayOf(locationId.toString()))
        val boardGames = mutableListOf<BoardGame>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(BG_L_COLUMN_GAME_ID))
                boardGames.add(getBoardGameById(id))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return boardGames
    }

    fun updateBoardGame(boardGameId: Int, boardGame: BoardGame) {
        val contentValues = ContentValues()

        contentValues.put(BG_COLUMN_BGG_ID, boardGame.bggId)
        contentValues.put(BG_COLUMN_TITLE, boardGame.title)
        contentValues.put(BG_COLUMN_ORIGINAL_TITLE, boardGame.originalTitle)
        contentValues.put(BG_COLUMN_YEAR_PUBLISHED, boardGame.yearPublished)
        contentValues.put(BG_COLUMN_DESCRIPTION, boardGame.description)
        contentValues.put(BG_COLUMN_ORDER_DATE,
            boardGame.orderDate?.let { Converter.convertLocalDateToEpochDay(it) })
        contentValues.put(BG_COLUMN_ADDED_TO_COLLECTION_DATE,
            boardGame.addedToCollectionDate?.let { Converter.convertLocalDateToEpochDay(it) })
        contentValues.put(BG_COLUMN_PRICE, boardGame.price)
        contentValues.put(BG_COLUMN_SUGGESTED_RETAIL_PRICE, boardGame.suggestedRetailPrice)
        contentValues.put(BG_COLUMN_EAN_OR_UPC, boardGame.eanOrUpc)
        contentValues.put(BG_COLUMN_PRODUCTION_CODE, boardGame.productionCode)
        contentValues.put(BG_COLUMN_TYPE, boardGame.type)
        contentValues.put(BG_COLUMN_COMMENT, boardGame.comment)
        contentValues.put(BG_COLUMN_THUMBNAIL, boardGame.thumbnail)

        db.update(
            TABLE_BOARD_GAMES, contentValues, "$BG_COLUMN_ID = ?",
            arrayOf(boardGameId.toString())
        )
    }

    fun deleteBoardGame(id: Int) {
        db.delete(
            TABLE_BOARD_GAMES, "$BG_COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }
}