package com.example.boardgamecollector.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boardgamecollector.R
import com.example.boardgamecollector.adapter.BggUserCollectionAdapter
import com.example.boardgamecollector.bggwebsite.downloader.BoardGameUserCollectionDownloader
import com.example.boardgamecollector.data.BoardGame
import kotlinx.android.synthetic.main.activity_board_game_geek.*

class BoardGameGeekActivity : AppCompatActivity(), BggUserCollectionAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_geek)

        val userName: EditText = findViewById(R.id.editTextUserNameGeekId)

        buttonSearchUserGeekId.setOnClickListener {
            val boardGamesList = getBoardGamesByUserName(userName.text.toString())
            displayBoardGamesList(boardGamesList)
        }
    }

    private fun getBoardGamesByUserName(userName: String): List<BoardGame> {
        val downloader = BoardGameUserCollectionDownloader()
        return downloader.execute(userName).get()
    }

    private fun displayBoardGamesList(boardGamesList: List<BoardGame>) {
        val adapter =
            BggUserCollectionAdapter(
                this,
                boardGamesList,
                this
            )
        recyclerViewSearchUserGeek.layoutManager = LinearLayoutManager(this)
        recyclerViewSearchUserGeek.adapter = adapter
    }

    override fun onItemClick(game: BoardGame) {
        TODO("Not yet implemented")
    }
}