package com.example.boardgamecollector.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?,
                      version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val CREATE_BOARD_GAMES_TABLE =
                "CREATE TABLE " + TABLE_BOARD_GAMES + " (" +
                        BG_COLUMN_ID + " INTEGER PRIMARY KEY," +
                        BG_COLUMN_BGG_ID + " INTEGER," +
                        BG_COLUMN_TITLE + " TEXT," +
                        BG_COLUMN_ORIGINAL_TITLE + " TEXT," +
                        BG_COLUMN_YEAR_PUBLISHED + " INTEGER," +
                        BG_COLUMN_DESCRIPTION + " TEXT," +
                        BG_COLUMN_ORDER_DATE + " INTEGER," +
                        BG_COLUMN_ADDED_TO_COLLECTION_DATE + " INTEGER," +
                        BG_COLUMN_PRICE + " REAL," +
                        BG_COLUMN_SUGGESTED_RETAIL_PRICE + " REAL," +
                        BG_COLUMN_EAN_OR_UPC + " TEXT," +
                        BG_COLUMN_PRODUCTION_CODE + " TEXT," +
                        BG_COLUMN_TYPE + " TEXT," +
                        BG_COLUMN_COMMENT + " TEXT," +
                        BG_COLUMN_THUMBNAIL + " TEXT)"

        private const val CREATE_DESIGNERS_TABLE =
                "CREATE TABLE " + TABLE_DESIGNERS + " (" +
                        D_COLUMN_ID + " INTEGER PRIMARY KEY," +
                        D_COLUMN_NAME + " TEXT)"

        private const val CREATE_ARTISTS_TABLE =
                "CREATE TABLE " + TABLE_ARTISTS + " (" +
                        A_COLUMN_ID + " INTEGER PRIMARY KEY," +
                        A_COLUMN_NAME + " TEXT)"

        private const val CREATE_LOCATIONS_TABLE =
                "CREATE TABLE " + TABLE_LOCATIONS + " (" +
                        L_COLUMN_ID + " INTEGER PRIMARY KEY," +
                        L_COLUMN_NAME + " TEXT)"

        private const val CREATE_RANKING_POSITIONS_TABLE =
            "CREATE TABLE " + TABLE_RANKING_POSITIONS + " (" +
                    RP_COLUMN_BGG_ID + " INTEGER," +
                    RP_COLUMN_DATE + " TEXT," +
                    RP_COLUMN_RANKING_POSITION + " INTEGER," +
                    " PRIMARY KEY(" + RP_COLUMN_BGG_ID + ", " + RP_COLUMN_DATE + "))"

        private const val CREATE_BOARD_GAMES_DESIGNERS_TABLE =
            "CREATE TABLE " + TABLE_BOARD_GAMES_DESIGNERS + " (" +
                    BG_D_COLUMN_GAME_ID + " INTEGER," +
                    BG_D_COLUMN_DESIGNER_ID + " INTEGER," +
                    " PRIMARY KEY(" + BG_D_COLUMN_GAME_ID + ", " +
                    BG_D_COLUMN_DESIGNER_ID + ")" +
                    " FOREIGN KEY(" + BG_D_COLUMN_GAME_ID +
                    ") REFERENCES " + TABLE_BOARD_GAMES +
                    " (" + BG_COLUMN_ID + ") ON DELETE CASCADE," +
                    " FOREIGN KEY(" + BG_D_COLUMN_DESIGNER_ID +
                    ") REFERENCES " + TABLE_DESIGNERS +
                    " (" + D_COLUMN_ID + ") ON DELETE CASCADE)"

        private const val CREATE_BOARD_GAMES_ARTISTS_TABLE =
            "CREATE TABLE " + TABLE_BOARD_GAMES_ARTISTS + " (" +
                    BG_A_COLUMN_GAME_ID + " INTEGER," +
                    BG_A_COLUMN_ARTIST_ID + " INTEGER," +
                    " PRIMARY KEY(" + BG_A_COLUMN_GAME_ID + ", " +
                    BG_A_COLUMN_ARTIST_ID + ")," +
                    " FOREIGN KEY(" + BG_A_COLUMN_GAME_ID +
                    ") REFERENCES " + TABLE_BOARD_GAMES +
                    " (" + BG_COLUMN_ID + ") ON DELETE CASCADE," +
                    " FOREIGN KEY(" + BG_A_COLUMN_ARTIST_ID +
                    ") REFERENCES " + TABLE_ARTISTS +
                    " (" + A_COLUMN_ID + ") ON DELETE CASCADE)"

        private const val CREATE_BOARD_GAMES_LOCATIONS_TABLE =
            "CREATE TABLE " + TABLE_BOARD_GAMES_LOCATIONS + " (" +
                    BG_L_COLUMN_GAME_ID + " INTEGER," +
                    BG_L_COLUMN_LOCATION_ID + " INTEGER," +
                    " PRIMARY KEY(" + BG_L_COLUMN_GAME_ID + ")," +
                    " FOREIGN KEY(" + BG_L_COLUMN_GAME_ID +
                    ") REFERENCES " + TABLE_BOARD_GAMES +
                    " (" + BG_COLUMN_ID + ") ON DELETE CASCADE," +
                    " FOREIGN KEY(" + BG_L_COLUMN_LOCATION_ID +
                    ") REFERENCES " + TABLE_LOCATIONS +
                    " (" + L_COLUMN_ID + ") ON DELETE CASCADE)"

        private const val DELETE_BOARD_GAMES_TABLE = "DROP TABLE IF EXISTS $TABLE_BOARD_GAMES"
        private const val DELETE_DESIGNERS_TABLE = "DROP TABLE IF EXISTS $TABLE_DESIGNERS"
        private const val DELETE_ARTISTS_TABLE = "DROP TABLE IF EXISTS $TABLE_ARTISTS"
        private const val DELETE_LOCATIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_LOCATIONS"
        private const val DELETE_RANKING_POSITIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_RANKING_POSITIONS"
        private const val DELETE_BOARD_GAMES_DESIGNERS_TABLE = "DROP TABLE IF EXISTS $TABLE_BOARD_GAMES_DESIGNERS"
        private const val DELETE_BOARD_GAMES_ARTISTS_TABLE = "DROP TABLE IF EXISTS $TABLE_BOARD_GAMES_ARTISTS"
        private const val DELETE_BOARD_GAMES_LOCATIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_BOARD_GAMES_LOCATIONS"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_BOARD_GAMES_TABLE)
        db?.execSQL(CREATE_DESIGNERS_TABLE)
        db?.execSQL(CREATE_ARTISTS_TABLE)
        db?.execSQL(CREATE_LOCATIONS_TABLE)
        db?.execSQL(CREATE_RANKING_POSITIONS_TABLE)
        db?.execSQL(CREATE_BOARD_GAMES_DESIGNERS_TABLE)
        db?.execSQL(CREATE_BOARD_GAMES_ARTISTS_TABLE)
        db?.execSQL(CREATE_BOARD_GAMES_LOCATIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DELETE_BOARD_GAMES_TABLE)
        db?.execSQL(DELETE_DESIGNERS_TABLE)
        db?.execSQL(DELETE_ARTISTS_TABLE)
        db?.execSQL(DELETE_LOCATIONS_TABLE)
        db?.execSQL(DELETE_RANKING_POSITIONS_TABLE)
        db?.execSQL(DELETE_BOARD_GAMES_DESIGNERS_TABLE)
        db?.execSQL(DELETE_BOARD_GAMES_ARTISTS_TABLE)
        db?.execSQL(DELETE_BOARD_GAMES_LOCATIONS_TABLE)
        onCreate(db)
    }
}