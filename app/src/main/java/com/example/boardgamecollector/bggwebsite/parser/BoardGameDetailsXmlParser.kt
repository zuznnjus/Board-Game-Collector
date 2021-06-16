
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        var tag: String? = ""
        var event = parser.eventType
        var text: String? = ""

        var originalTitle: String? = null
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
                    "name" -> {
                        val type = parser.getAttributeValue(null, "type")

                        if (type == "primary") {
                            originalTitle = parser.getAttributeValue(null, "value")
                        }
                    }
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
                        val value = parser.getAttributeValue(null, "value")
                        if (type == "subtype" && value != "Not Ranked") {
                            rankingPosition = value.toInt()
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

        return BoardGame(
            yearPublished = yearPublished,
            originalTitle = originalTitle,
            description = description,
            thumbnail = thumbnail,
            designers = designers,
            artists = artists,
            ranking = rankingPosition
        )
    }
}