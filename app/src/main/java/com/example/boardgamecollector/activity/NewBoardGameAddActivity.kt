package com.example.boardgamecollector.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.boardgamecollector.R
import com.example.boardgamecollector.bggwebsite.downloader.BoardGameDetailsDownloader
import com.example.boardgamecollector.data.Artist
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.data.Location
import com.example.boardgamecollector.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_board_game_add.*
import java.lang.Exception
import java.time.LocalDate

class NewBoardGameAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_board_game_add)

        val gameOverview: BoardGame? = intent.extras?.getParcelable(BGG_MESSAGE)

        Thread(this::setupLocationSpinner).run()

        if (gameOverview != null) {
            val boardGame = getBoardGameDetails(gameOverview.bggId)
            fillAllData(gameOverview, boardGame)
        }

        val buttonSaveGame: Button = findViewById(R.id.buttonSaveGame)
        buttonSaveGame.setOnClickListener {
            insertBoardGame();
            showMainActivity();
        }
    }

    private fun setupLocationSpinner() {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)

        val locations = locationHandler.getAllLocations()
        val spinner: Spinner = findViewById(R.id.spinnerLocation)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, locations
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun getBoardGameDetails(bggId: Int): BoardGame {
        val downloader = BoardGameDetailsDownloader()
        return downloader.execute(bggId).get()
    }

    private fun fillAllData(gameOverview: BoardGame, boardGame: BoardGame) {
        editTextBggValueId.setText(gameOverview.bggId.toString())
        editTextTitleId.setText(gameOverview.originalTitle ?: "")
        editTextOriginalTitleId.setText(boardGame.originalTitle ?: "")
        editTextYearId.setText(boardGame.yearPublished?.toString() ?: "")
        editTextDescriptionId.setText(boardGame.description ?: "")
        editTextOrderDateId.setText(LocalDate.now().toString())
        editTextAddToCollectionDateId.setText(LocalDate.now().toString())
        editTextPriceId.setText(boardGame.price?.toString() ?: "")
        editTextSuggestedRetailPriceId.setText(boardGame.suggestedRetailPrice?.toString() ?: "")
        editTextEanOrUpcId.setText(boardGame.eanOrUpc ?: "")
        editTextProductionCodeId.setText(boardGame.productionCode ?: "")
        editTextRankingValueId.setText(boardGame.ranking?.toString() ?: "")
        editTextTypeId.setText(boardGame.type ?: "")
        editTextCommentId.setText(boardGame.comment ?: "")

        editTextThumbnail.setText(boardGame.thumbnail ?: "")
        editTextThumbnail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !boardGame.thumbnail.isNullOrBlank()) {
                Picasso.get().load(editTextThumbnail.text.toString()).into(imageViewThumbnailId);
            }
        }
        if (!boardGame.thumbnail.isNullOrBlank()) {
            Picasso.get().load(boardGame.thumbnail).into(imageViewThumbnailId);
        }

        editTextDesigners.setText(boardGame.designers.joinToString(",") { it.name });
        editTextArtists.setText(boardGame.artists.joinToString(",") { it.name });
    }

    private fun insertBoardGame() {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)
        val designerHandler = DesignerHandler(dataBaseHandler)
        val artistHandler = ArtistHandler(dataBaseHandler)
        val rankingHandler = RankingHandler(dataBaseHandler)

        val bggId = editTextBggValueId.text.toString().toIntOrNull() ?: 0
        val title = editTextTitleId.text.toString()
        val originalTitle = editTextOriginalTitleId.text.toString()
        val year = editTextYearId.text.toString().toIntOrNull()
        val description = editTextDescriptionId.text.toString()
        val orderDate = try {
            LocalDate.parse(editTextOrderDateId.text.toString())
        } catch (e: Exception) {
            LocalDate.now()
        }
        val addToCollectionDate = try {
            LocalDate.parse(editTextAddToCollectionDateId.text.toString())
        } catch (e: Exception) {
            LocalDate.now()
        }
        val price = editTextPriceId.text.toString().toDoubleOrNull()
        val srp = editTextSuggestedRetailPriceId.text.toString().toDoubleOrNull() //? XD
        val eanOrUpc = editTextEanOrUpcId.text.toString()
        val productionCode = editTextProductionCodeId.text.toString()
        val rankingPosition = editTextRankingValueId.text.toString().toIntOrNull()
        val type = editTextTypeId.text.toString()
        val comment = editTextCommentId.text.toString()
        val thumbnail = editTextThumbnail.text.toString()
        val designersNames = editTextDesigners.text.toString().split(",")
        val artistsNames = editTextArtists.text.toString().split(",")
        val locationId = (spinnerLocation.selectedItem as? Location)?.id

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

        val gameId = boardGameHandler.insertBoardGame(boardGame)

        for (name in designersNames) {
            val designerId = designerHandler.insertDesigner(name)
            boardGameHandler.addDesigner(gameId, designerId)
        }

        for (name in artistsNames) {
            val artistId = artistHandler.insertArtist(Artist(0, name))
            boardGameHandler.addArtist(gameId, artistId)
        }

        if (bggId != 0 && rankingPosition != null && rankingPosition != 0) {
            rankingHandler.insertRankingPosition(bggId, rankingPosition)
        }

        boardGameHandler.setLocation(gameId, locationId)

        Toast.makeText(this, "The board game has been added", Toast.LENGTH_LONG).show()
    }

    private fun showMainActivity() {
        finish()
    }
}