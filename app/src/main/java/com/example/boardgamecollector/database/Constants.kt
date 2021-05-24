package com.example.boardgamecollector.database

// database name and version
const val DATABASE_VERSION = 1
const val DATABASE_NAME = "BoardGamesDB.db"

// table names
const val TABLE_BOARD_GAMES = "boardGames"
const val TABLE_DESIGNERS = "designers"
const val TABLE_ARTISTS = "artists"
const val TABLE_LOCATIONS = "locations"
const val TABLE_RANKING_POSITIONS = "rankingPositions"
const val TABLE_BOARD_GAMES_DESIGNERS = "boardGamesDesigners"
const val TABLE_BOARD_GAMES_ARTISTS = "boardGamesArtists"
const val TABLE_BOARD_GAMES_LOCATIONS = "boardGamesLocations"

// table BOARD_GAMES - column names
const val BG_COLUMN_ID = "id"
const val BG_COLUMN_BGG_ID = "bggId"
const val BG_COLUMN_TITLE = "title"
const val BG_COLUMN_ORIGINAL_TITLE = "originalTitle"
const val BG_COLUMN_YEAR_PUBLISHED = "yearPublished"
const val BG_COLUMN_DESCRIPTION = "description"
const val BG_COLUMN_ORDER_DATE = "orderDate"
const val BG_COLUMN_ADDED_TO_COLLECTION_DATE = "addedToCollectionDate"
const val BG_COLUMN_PRICE = "price"
const val BG_COLUMN_SUGGESTED_RETAIL_PRICE = "suggestedRetailPrice"
const val BG_COLUMN_EAN_OR_UPC = "eanOrUpc"
const val BG_COLUMN_PRODUCTION_CODE = "productionCode"
const val BG_COLUMN_TYPE = "type"
const val BG_COLUMN_COMMENT = "comment"
const val BG_COLUMN_THUMBNAIL = "thumbnail"

// table DESIGNERS - column names
const val D_COLUMN_ID = "designerId"
const val D_COLUMN_NAME = "designerName"

// table ARTISTS - column names
const val A_COLUMN_ID = "artistId"
const val A_COLUMN_NAME = "artistName"

// table LOCATIONS - column names
const val L_COLUMN_ID = "locationId"
const val L_COLUMN_NAME = "locationName"

// table RANKING_POSITIONS - column names
const val RP_COLUMN_BGG_ID = "bggId"
const val RP_COLUMN_DATE = "rankingDate"
const val RP_COLUMN_RANKING_POSITION = "rankingPosition"

// table BOARD_GAMES_DESIGNERS - column names
const val BG_D_COLUMN_GAME_ID = "gameId"
const val BG_D_COLUMN_DESIGNER_ID = "designerId"

// table BOARD_GAMES_ARTISTS - column names
const val BG_A_COLUMN_GAME_ID = "gameId"
const val BG_A_COLUMN_ARTIST_ID = "artistId"

// table BOARD_GAMES_LOCATIONS - column names
const val BG_L_COLUMN_GAME_ID = "gameId"
const val BG_L_COLUMN_LOCATION_ID = "locationId"

