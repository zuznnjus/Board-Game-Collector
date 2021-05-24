package com.example.boardgamecollector.bggwebsite.parser

import com.example.boardgamecollector.data.Artist
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.data.Designer
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class BoardGameDetailsXmlParser: XmlParser<BoardGame> {
    override fun parse(inputStream: InputStream): BoardGame {
        TODO("Not yet implemented")
    }
//    override fun parse(inputStream: InputStream): BoardGame {
//        val parserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
//        val parser: XmlPullParser = parserFactory.newPullParser()
//
//        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//        parser.setInput(inputStream, null)
//
//        var tag: String? = ""
//        var event = parser.eventType
//
//        var gameId: Int = 0
//        var thumbnail: String? = null
//        var description: String? = null
//        var yearPublished: Int? = null
//        var designers: List<Designer>? = null
//        var artists: List<Artist>? = null
//        var rankingPosition: Int? = null
//
//        lateinit var boardGame: BoardGame
//
//        while (event != XmlPullParser.END_DOCUMENT) {
//            tag = parser.name
//            when (event) {
//                XmlPullParser.START_TAG -> when (tag) {
//                    "item" -> {
//                        gameId = parser.getAttributeValue(null, "id").toInt()
//                    }
//                    "name" -> {
//                        gameType = parser.getAttributeValue(null, "type")
//                        gameName = parser.getAttributeValue(null, "value")
//                    }
//                    "yearpublished" -> {
//                        yearPublished = parser.getAttributeValue(null, "value").toInt()
//                    }
//                }
//                XmlPullParser.END_TAG -> when (tag) {
//                    "item" -> {
//                        boardGame = BoardGame(bggId = gameId, originalTitle = gameName,
//                            yearPublished = yearPublished, type = gameType)
//                    }
//                }
//            }
//            event = parser.next()
//        }
//
//        return boardGame
//    }
}