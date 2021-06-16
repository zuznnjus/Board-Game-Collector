package com.example.boardgamecollector.bggwebsite.parser

import com.example.boardgamecollector.data.BoardGame
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class BoardGameUserCollectionXmlParser: XmlParser<List<BoardGame>> {
    override fun parse(inputStream: InputStream): List<BoardGame> {
        val parserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        val parser: XmlPullParser = parserFactory.newPullParser()

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        var tag: String? = ""
        var event = parser.eventType
        var text: String? = ""

        var bggId: Int = 0
        var gameTitle: String? = null
        var yearPublished: Int? = null
        var gameType: String? = null
        var thumbnail: String? = null

        val boardGamesList = mutableListOf<BoardGame>()

        while (event != XmlPullParser.END_DOCUMENT) {
            tag = parser.name
            when (event) {
                XmlPullParser.START_TAG -> when (tag) {
                    "item" -> {
                        bggId = parser.getAttributeValue(null, "objectid").toInt()
                        gameType = parser.getAttributeValue(null, "subtype")
                    }
                }
                XmlPullParser.TEXT -> text = parser.text
                XmlPullParser.END_TAG -> when (tag) {
                    "name" -> gameTitle = text
                    "yearpublished" -> yearPublished = text?.toInt()
                    "thumbnail" -> thumbnail = text
                    "item" -> {
                        val boardGame = BoardGame(bggId = bggId, originalTitle = gameTitle,
                            yearPublished = yearPublished, type = gameType, thumbnail = thumbnail)
                        boardGamesList.add(boardGame)
                    }
                }
            }
            event = parser.next()
        }

        return boardGamesList
    }
}