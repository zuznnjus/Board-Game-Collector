package com.example.boardgamecollector.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.boardgamecollector.R
import com.example.boardgamecollector.data.Artist
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.data.Location
import com.example.boardgamecollector.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_board_games_details.*
import kotlinx.android.synthetic.main.activity_new_board_game_add.*
import java.time.LocalDate

class BoardGameDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_games_details)

        val extras = intent.extras ?: return
        val boardGameId = extras.getInt(BGG_MESSAGE)

        val boardGame = getBoardGame(boardGameId)
        fillAllData(boardGame)

        Thread(this::setupLocationSpinner).run()

        val buttonEditGame: Button = findViewById(R.id.buttonEditGame)
        buttonEditGame.setOnClickListener {
            editBoardGame(boardGameId)
        }

        val buttonRankingHistory: Button = findViewById(R.id.buttonGameRankingHistoryId)
        buttonRankingHistory.setOnClickListener {
            showRankingHistoryActivity(boardGame.bggId)
        }
    }

    private fun setupLocationSpinner() {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)

        val locations = locationHandler.getAllLocations()
        val spinner: Spinner = findViewById(R.id.spinnerDetailsLocation)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, locations
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun getBoardGame(boardGameId: Int): BoardGame {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)
        return boardGameHandler.getBoardGameById(boardGameId)
    }

    private fun fillAllData(boardGame: BoardGame) {
        editTextDetailsBggValueId.setText(boardGame.bggId.toString())
        editTextDetailsTitleId.setText(boardGame.title ?: "")
        editTextDetailsOriginalTitleId.setText(boardGame.originalTitle ?: "")
        editTextDetailsYearId.setText(boardGame.yearPublished?.toString() ?: "")
        editTextDetailsDescriptionId.setText(boardGame.description ?: "")
        editTextDetailsOrderDateId.setText(boardGame.orderDate.toString())
        editTextDetailsAddToCollectionDateId.setText(boardGame.addedToCollectionDate.toString())
        editTextDetailsPriceId.setText(boardGame.price?.toString() ?: "")
        editTextDetailsSuggestedRetailPriceId.setText(boardGame.suggestedRetailPrice?.toString() ?: "")
        editTextDetailsEanOrUpcId.setText(boardGame.eanOrUpc ?: "")
        editTextDetailsProductionCodeId.setText(boardGame.productionCode ?: "")
        editTextDetailsRankingValueId.setText(boardGame.ranking?.toString() ?: "")
        editTextDetailsTypeId.setText(boardGame.type ?: "")
        editTextDetailsCommentId.setText(boardGame.comment ?: "")

        editTextDetailsThumbnail.setText(boardGame.thumbnail ?: "")
        editTextDetailsThumbnail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !boardGame.thumbnail.isNullOrBlank()) {
                Picasso.get().load(editTextDetailsThumbnail.text.toString()).into(imageViewDetailsThumbnailId);
            }
        }
        if (!boardGame.thumbnail.isNullOrBlank()) {
            Picasso.get().load(boardGame.thumbnail).into(imageViewDetailsThumbnailId);
        }

        editTextDetailsDesigners.setText(boardGame.designers.joinToString(",") { it.name });
        editTextDetailsArtists.setText(boardGame.artists.joinToString(",") { it.name });
    }

    private fun editBoardGame(gameId: Int) {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)

        val bggId = editTextDetailsBggValueId.text.toString().toInt()
        val title = editTextDetailsTitleId.text.toString()
        val originalTitle = editTextDetailsOriginalTitleId.text.toString()
        val year = editTextDetailsYearId.text.toString().toIntOrNull()
        val description = editTextDetailsDescriptionId.text.toString()
        val orderDate = LocalDate.parse(editTextDetailsOrderDateId.text.toString())
        val addToCollectionDate = LocalDate.parse(editTextDetailsAddToCollectionDateId.text.toString())
        val price = editTextDetailsPriceId.text.toString().toDoubleOrNull()
        val srp = editTextDetailsSuggestedRetailPriceId.text.toString().toDoubleOrNull()
        val eanOrUpc = editTextDetailsEanOrUpcId.text.toString()
        val productionCode = editTextDetailsProductionCodeId.text.toString()
        val type = editTextDetailsTypeId.text.toString()
        val comment = editTextDetailsCommentId.text.toString()
        val thumbnail = editTextDetailsThumbnail.text.toString()
        val locationId = (spinnerDetailsLocation.selectedItem as? Location)?.id

        val boardGame = BoardGame(
            bggId = bggId,
            title = title,
            originalTitle = originalTitle,
            yearPublished = year,
            description = description,
            price = price,
            suggestedRetailPrice = srp,
            orderDate = orderDate,
            addedToCollectionDate = addToCollectionDate,
            thumbnail = thumbnail,
            eanOrUpc = eanOrUpc,
            productionCode = productionCode,
            type = type,
            comment = comment
        )

        boardGameHandler.updateBoardGame(gameId, boardGame)
        Toast.makeText(this, "The board game has been updated", Toast.LENGTH_LONG).show()
        boardGameHandler.setLocation(gameId, locationId)
    }

    private fun showRankingHistoryActivity(boardGameId: Int) {
        val intent = Intent(this, BoardGameRankingActivity::class.java)
        intent.putExtra(BGG_MESSAGE, boardGameId)
        startActivity(intent)
    }
}