package com.example.boardgamecollector.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boardgamecollector.R
import com.example.boardgamecollector.adapter.SearchBoardGameAdapter
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.database.BoardGameHandler
import com.example.boardgamecollector.database.DataBaseHandler
import com.example.boardgamecollector.database.LocationHandler
import kotlinx.android.synthetic.main.activity_board_game_location.*

class BoardGameLocationActivity : AppCompatActivity(), SearchBoardGameAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_location)

        val extras = intent.extras ?: return
        val locationId = extras.getInt(BGG_MESSAGE)

        val boardGamesList = getAllBoardGames(locationId)
        displayBoardGamesList(boardGamesList)

        val locationName = getLocationName(locationId)
        editTextGameLocationNameId.setText(locationName)

        buttonEditGameLocationId.setOnClickListener {
            editLocationName(locationId)
        }
    }

    private fun getAllBoardGames(locationId: Int): List<BoardGame> {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)

        return boardGameHandler.getBoardGamesInLocation(locationId)
    }

    private fun getLocationName(locationId: Int): String {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)

        return locationHandler.getLocationName(locationId)
    }

    private fun editLocationName(locationId: Int) {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)

        locationHandler.updateLocation(locationId, editTextGameLocationNameId.text.toString())
        Toast.makeText(this, "The location has been edited", Toast.LENGTH_LONG).show()
    }

    private fun displayBoardGamesList(boardGamesList: List<BoardGame>) {
        val adapter =
            SearchBoardGameAdapter(
                this,
                boardGamesList,
                this
            )
        recyclerViewGamesInLocation.layoutManager = LinearLayoutManager(this)
        recyclerViewGamesInLocation.adapter = adapter
    }

    override fun onItemClick(game: BoardGame) {
    }

}