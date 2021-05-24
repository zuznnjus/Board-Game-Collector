package com.example.boardgamecollector.bggwebsite.downloader

import android.os.AsyncTask
import com.example.boardgamecollector.bggwebsite.parser.BoardGameDetailsXmlParser
import com.example.boardgamecollector.data.BoardGame

class BoardGameDetailsDownloader: AsyncTask<Int, Int, BoardGame>() {
    override fun doInBackground(vararg params: Int?): BoardGame {
        return getBoardGameDetails(params[0])
    }

    private fun getBoardGameDetails(bggId: Int?): BoardGame {
        val url = java.net.URL(String.format(BOARD_GAME_DETAILS_URL, bggId))
        val connection = url.openConnection()
        connection.connect()
        val stream = url.openStream()
        val parser = BoardGameDetailsXmlParser()
        val boardGame: BoardGame = parser.parse(stream)
        stream.close()

        return boardGame
    }
}