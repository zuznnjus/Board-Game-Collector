package com.example.boardgamecollector.bggwebsite.parser

import com.example.boardgamecollector.data.Artist
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.data.Designer
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class BoardGameDetailsXmlParser : XmlParser<BoardGame> {
    override fun parse(inputStream: InputStream): BoardGame {
        val parserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        val parser: XmlPullParser = parserFactory.newPullParser()

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        var tag: String? = ""
        var event = parser.eventType
        var text: String? = ""

        var thumbnail: String? = null
        var description: String? = null
        var yearPublished: Int? = null
        val designers = mutableListOf<Designer>()
        val artists = mutableListOf<Artist>()
        var rankingPosition: Int? = null

        while (event != XmlPullParser.END_DOCUMENT) {
            tag = parser.name
            when (event) {
                XmlPullParser.START_TAG -> when (tag) {
                    "yearpublished" -> {
                        yearPublished = parser.getAttributeValue(null, "value").toInt()
                    }
                    "link" -> {
                        val type = parser.getAttributeValue(null, "type")

                        if (type == "boardgamedesigner") {
                            val designerId = parser.getAttributeValue(null, "id").toInt()
                            val designerName = parser.getAttributeValue(null, "value")
                            designers.add(Designer(designerId, designerName))
                        } else if (type == "boardgameartist") {
                            val artistId = parser.getAttributeValue(null, "id").toInt()
                            val artistName = parser.getAttributeValue(null, "value")
                            artists.add(Artist(artistId, artistName))
                        }
                    }
                    "rank" -> {
                        val type = parser.getAttributeValue(null, "type")

                        if (type == "subtype") {
                            rankingPosition = parser.getAttributeValue(null, "value").toInt()
                        }
                    }
                }
                XmlPullParser.TEXT -> text = parser.text
                XmlPullParser.END_TAG -> when (tag) {
                    "thumbnail" -> thumbnail = text
                    "description" -> description = text
                }
            }
            event = parser.next()
        }

        // TODO co z artystami, designerami i rankingiem?
        return BoardGame(yearPublished = yearPublished, description = description,
            thumbnail = thumbnail)
    }
}