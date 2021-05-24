package com.example.boardgamecollector.bggwebsite.downloader

import android.os.AsyncTask
import com.example.boardgamecollector.bggwebsite.parser.BoardGameDataXmlParser
import com.example.boardgamecollector.data.BoardGame
import java.net.URL

class BoardGameDataDownloader : AsyncTask<String, Int, List<BoardGame>>() {
    override fun doInBackground(vararg params: String?): List<BoardGame> {
        return getBoardGameData(params[0])
    }

    private fun getBoardGameData(gameTitle: String?): List<BoardGame> {
        val url = URL(String.format(BOARD_GAME_DATA_URL, gameTitle))
        val connection = url.openConnection()
        connection.connect()
        val stream = url.openStream()
        val parser = BoardGameDataXmlParser()
        val boardGamesList: List<BoardGame> = parser.parse(stream)
        stream.close()

        return boardGamesList
    }
}
