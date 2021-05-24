package com.example.boardgamecollector.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.boardgamecollector.R
import com.example.boardgamecollector.bggwebsite.downloader.BoardGameDataDownloader
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.database.BoardGameHandler
import com.example.boardgamecollector.database.DataBaseHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DataBaseHandler(this, null, null, 1)
        val bgdb = BoardGameHandler(db)
        val game = BoardGame(title = "kawerna", originalTitle = "kawerna", type = "boardgame")
        bgdb.insertBoardGame(game)
        val textView: TextView = findViewById(R.id.textViewTestId)
    }


}