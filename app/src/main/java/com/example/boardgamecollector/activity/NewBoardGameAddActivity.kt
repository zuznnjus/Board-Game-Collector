package com.example.boardgamecollector.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.boardgamecollector.R
import com.example.boardgamecollector.bggwebsite.downloader.BoardGameDetailsDownloader
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.database.BoardGameHandler
import com.example.boardgamecollector.database.DataBaseHandler
import kotlinx.android.synthetic.main.activity_new_board_game_add.*

class NewBoardGameAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_board_game_add)

        val extras = intent.extras ?: return
        val bggId = extras.getInt(BGG_ID_MESSAGE)

        if (bggId != EMPTY_MESSAGE) {
            val boardGame= getBoardGameDetails(bggId)
            fillAllData(boardGame)
        }

        val buttonSaveGame: Button = findViewById(R.id.buttonSaveGame)
        buttonSaveGame.setOnClickListener {
            insertBoardGame();
        }
    }

    private fun getBoardGameDetails(bggId: Int): BoardGame {
        val downloader = BoardGameDetailsDownloader()
        return downloader.execute(bggId).get()
    }

    private fun fillAllData(boardGame: BoardGame) {
        editTextBggValueId.text = boardGame.bggId.toString()
        editTextTitleId.setText(boardGame.title ?:"")
        editTextOriginalTitleId.setText(boardGame.originalTitle  ?:"")
        editTextYearId.setText(boardGame.yearPublished.toString() ?:"")
        editTextDescriptionId.setText(boardGame.description ?:"")
        //editTextOrderDateId
        //editTextAddToCollectionDate
        editTextPriceId.setText(boardGame.price.toString() ?:"")
        editTextSuggestedRetailPriceId.setText(boardGame.suggestedRetailPrice.toString() ?:"")
        editTextEanOrUpcId.setText(boardGame.eanOrUpc ?:"")
        editTextProductionCodeId.setText(boardGame.productionCode ?:"")
        //editTextRankingValueId
        editTextTypeId.setText(boardGame.type ?:"")
        editTextCommentId.setText(boardGame.comment ?:"")
        //imageViewThumbnailId
    }

    private fun insertBoardGame() {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)

        val bggId = editTextBggValueId.text.toString().toInt()
        val title = editTextTitleId.text.toString()
        val originalTitle = editTextOriginalTitleId.text.toString()
        val year = editTextYearId.text.toString().toInt()
        val description = editTextDescriptionId.text.toString()
        //editTextOrderDateId
        //editTextAddToCollectionDate
        val price = editTextPriceId.text.toString().toDouble()
        val srp = editTextSuggestedRetailPriceId.text.toString().toDouble() //? XD
        val eanOrUpc = editTextEanOrUpcId.text.toString()
        val productionCode = editTextProductionCodeId.text.toString()
        //editTextRankingValueId
        val type = editTextTypeId.text.toString()
        val comment = editTextCommentId.text.toString()
        //imageViewThumbnailId

        val boardGame = BoardGame(bggId = bggId, title = title, originalTitle = originalTitle, yearPublished = year,
            description = description, price = price, suggestedRetailPrice = srp,
            eanOrUpc = eanOrUpc, productionCode = productionCode, type = type, comment = comment)

        boardGameHandler.insertBoardGame(boardGame)
    }
}