package com.example.boardgamecollector.bggwebsite.parser

import com.example.boardgamecollector.data.BoardGame
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class BoardGameDataXmlParser: XmlParser<List<BoardGame>> {
    override fun parse(inputStream: InputStream): List<BoardGame> {
        val parserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        val parser: XmlPullParser = parserFactory.newPullParser()

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        var tag: String? = ""
        var event = parser.eventType

        var gameId: Int = 0
        var gameType: String? = null
        var gameName: String? = null
        var yearPublished: Int? = null
        var boardGamesList = mutableListOf<BoardGame>()

        while (event != XmlPullParser.END_DOCUMENT) {
            tag = parser.name
            when (event) {
                XmlPullParser.START_TAG -> when (tag) {
                        "item" -> {
                            gameId = parser.getAttributeValue(null, "id").toInt()
                        }
                        "name" -> {
                            gameType = parser.getAttributeValue(null, "type")
                            gameName = parser.getAttributeValue(null, "value")
                        }
                        "yearpublished" -> {
                            yearPublished = parser.getAttributeValue(null, "value").toInt()
                        }
                    }
                XmlPullParser.END_TAG -> when (tag) {
                    "item" -> {
                        val boardGame = BoardGame(bggId = gameId, originalTitle = gameName,
                        yearPublished = yearPublished, type = gameType)
                        boardGamesList.add(boardGame)
                    }
                }
            }
            event = parser.next()
        }

        return boardGamesList
    }
}