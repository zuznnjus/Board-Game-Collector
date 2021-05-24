package com.example.boardgamecollector.bggwebsite.downloader

import android.os.AsyncTask
import com.example.boardgamecollector.bggwebsite.parser.BoardGameUserCollectionXmlParser
import com.example.boardgamecollector.data.BoardGame

class BoardGameUserCollectionDownloader: AsyncTask<String, Int, List<BoardGame>>() {
    override fun doInBackground(vararg params: String?): List<BoardGame> {
        return getBoardGameUserCollection(params[0])
    }

    private fun getBoardGameUserCollection(userName: String?): List<BoardGame> {
        val url = java.net.URL(String.format(BOARD_GAME_USER_COLLECTION_URL, userName))
        val connection = url.openConnection()
        connection.connect()
        val stream = url.openStream()
        val parser = BoardGameUserCollectionXmlParser()
        val boardGamesList: List<BoardGame> = parser.parse(stream)
        stream.close()

        return boardGamesList
    }
}