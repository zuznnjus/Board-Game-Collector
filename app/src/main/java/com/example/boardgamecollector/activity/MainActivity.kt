package com.example.boardgamecollector.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.boardgamecollector.R
import com.example.boardgamecollector.database.BoardGameHandler
import com.example.boardgamecollector.database.DataBaseHandler

class MainActivity : AppCompatActivity() {
    private val orderByList = listOf("title", "year", "ranking")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayUserBoardGames(orderByList[0])

        val spinner = createOrderBySpinner()
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                val orderByElement = parent.getItemAtPosition(position) as String
                displayUserBoardGames(orderByElement)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val buttonNewBoardGame: Button = findViewById(R.id.buttonNewBoardGameId)
        buttonNewBoardGame.setOnClickListener {
            showNewBoardGameActivity()
        }

        val buttonBggScreen: Button = findViewById(R.id.buttonBggScreenId)
        buttonBggScreen.setOnClickListener {
            showBoardGameGeekActivity()
        }

        val buttonLocations: Button = findViewById(R.id.buttonLocationsId)
        buttonLocations.setOnClickListener {
            showBoardGameLocationsActivity()
        }
    }

    private fun createOrderBySpinner(): Spinner {
        val spinner: Spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, orderByList)
        spinner.adapter = adapter

        return spinner
    }

    private fun displayUserBoardGames(orderByElement: String) {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)
        val boardGameList = boardGameHandler.getAllBoardGames()
    }

    private fun showNewBoardGameActivity() {
        val intent = Intent(this, NewBoardGameSearchActivity::class.java)
        startActivity(intent)
    }

    private fun showBoardGameGeekActivity() {
        val intent = Intent(this, BoardGameGeekActivity::class.java)
        startActivity(intent)
    }

    private fun showBoardGameLocationsActivity() {
        val intent = Intent(this, BoardGameLocationActivity::class.java)
        startActivity(intent)
    }
}