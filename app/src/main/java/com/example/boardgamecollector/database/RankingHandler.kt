package com.example.boardgamecollector.database

import android.content.ContentValues
import com.example.boardgamecollector.data.Ranking
import java.time.LocalDateTime

class RankingHandler(dataBaseHandler: DataBaseHandler) {
    private val db = dataBaseHandler.writableDatabase

    fun insertRankingPosition(bggId: Int, position: Int) {
        val contentValues = ContentValues()
        contentValues.put(RP_COLUMN_BGG_ID, bggId)
        contentValues.put(RP_COLUMN_DATE, LocalDateTime.now().toString())
        contentValues.put(RP_COLUMN_RANKING_POSITION, position)

        db.insert(TABLE_RANKING_POSITIONS, null, contentValues)
    }

    fun getRankingPosition(bggId: Int): Int {
        var rankingPosition: Int = 0
        val query = "SELECT * FROM $TABLE_RANKING_POSITIONS " +
                "WHERE $RP_COLUMN_BGG_ID = $bggId ORDER BY $RP_COLUMN_DATE DESC"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            rankingPosition = cursor.getInt(
                cursor.getColumnIndex(RP_COLUMN_RANKING_POSITION)
            )
        }

        cursor.close()
        return rankingPosition
    }

    fun getAllRankingPositions(bggId: Int): List<Ranking> {
        val rankingPositions = mutableListOf<Ranking>()

        val query = "SELECT * FROM $TABLE_RANKING_POSITIONS " +
                "WHERE $RP_COLUMN_BGG_ID = $bggId ORDER BY $RP_COLUMN_DATE DESC"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val date = Converter.convertEpochDayToLocalDate(
                    cursor.getLong(cursor.getColumnIndex(RP_COLUMN_DATE))
                )
                val rankingPosition = cursor.getInt(
                    cursor.getColumnIndex(RP_COLUMN_RANKING_POSITION)
                )

                val ranking = Ranking(bggId, date, rankingPosition)
                rankingPositions.add(ranking)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return rankingPositions
    }
}