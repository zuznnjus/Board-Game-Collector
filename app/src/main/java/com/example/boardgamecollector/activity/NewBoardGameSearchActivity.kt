package com.example.boardgamecollector.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boardgamecollector.R
import com.example.boardgamecollector.adapter.SearchBoardGameAdapter
import com.example.boardgamecollector.bggwebsite.downloader.BoardGameDataDownloader
import com.example.boardgamecollector.data.BoardGame
import kotlinx.android.synthetic.main.activity_new_board_game_search.*

const val BGG_MESSAGE = "bgg"

class NewBoardGameSearchActivity : AppCompatActivity(), SearchBoardGameAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_board_game_search)

        val gameTitle: EditText = findViewById(R.id.editTextTitleId)
        var boardGamesList: List<BoardGame>

        buttonSearchBoardGameId.setOnClickListener {
            boardGamesList = getBoardGamesByTitle(gameTitle.text.toString())
            displayBoardGamesList(boardGamesList)
        }

        buttonAddCustomBoardGameId.setOnClickListener {
            showAddBoardGameActivity()
        }
    }

    private fun getBoardGamesByTitle(gameTitle: String): List<BoardGame> {
        val downloader = BoardGameDataDownloader()
        return downloader.execute(gameTitle).get()
    }

    private fun displayBoardGamesList(boardGamesList: List<BoardGame>) {
        val adapter =
            SearchBoardGameAdapter(
                this,
                boardGamesList,
                this
            )
        recyclerViewSearchGame.layoutManager = LinearLayoutManager(this)
        recyclerViewSearchGame.adapter = adapter
    }

    private fun showAddBoardGameActivity() {
        val intent = Intent(this, NewBoardGameAddActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(game: BoardGame) {
        val intent = Intent(this, NewBoardGameAddActivity::class.java)
        intent.putExtra(BGG_MESSAGE, game)
        startActivity(intent)
    }
}