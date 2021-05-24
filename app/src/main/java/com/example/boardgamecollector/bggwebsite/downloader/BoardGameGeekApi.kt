package com.example.boardgamecollector.bggwebsite.downloader

private const val URL = "https://www.boardgamegeek.com/xmlapi2/"
const val BOARD_GAME_DATA_URL = "${URL}search?query=%s&type=boardgame"
const val BOARD_GAME_DETAILS_URL = "${URL}thing?id=%d&stats=1"
const val BOARD_GAME_USER_COLLECTION_URL = "${URL}collection?username=%s"