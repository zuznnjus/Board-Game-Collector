package com.example.boardgamecollector.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boardgamecollector.R
import com.example.boardgamecollector.adapter.LocationAdapter
import com.example.boardgamecollector.data.Location
import com.example.boardgamecollector.database.DataBaseHandler
import com.example.boardgamecollector.database.LocationHandler
import kotlinx.android.synthetic.main.activity_board_game_location_list.*

class BoardGameLocationListActivity : AppCompatActivity(), LocationAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_location_list)

        val locationsList = getLocations()
        displayLocationsList(locationsList)

        val buttonNewLocation: Button = findViewById(R.id.buttonAddNewLocationId)
        buttonNewLocation.setOnClickListener {
            insertLocation()
        }
    }

    override fun onRestart() {
        super.onRestart()
        displayLocationsList(getLocations())
    }

    private fun insertLocation() {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)
        val locationName = editTextLocationNameId.text.toString()

        locationHandler.insertLocation(locationName)
        editTextLocationNameId.setText("")
        Toast.makeText(this, "The location has been added", Toast.LENGTH_LONG).show()

        Thread {
            displayLocationsList(getLocations())
        }.run()
    }

    private fun getLocations(): List<Location> {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)

        return locationHandler.getAllLocations()
    }

    private fun displayLocationsList(locationsList: List<Location>) {
        val adapter =
            LocationAdapter(
                this,
                locationsList,
                this,
                this::deleteLocation
            )
        recyclerViewLocations.layoutManager = LinearLayoutManager(this)
        recyclerViewLocations.adapter = adapter
    }

    override fun onItemClick(location: Location) {
        val intent = Intent(this, BoardGameLocationActivity::class.java)
        intent.putExtra(BGG_MESSAGE, location.id)
        startActivity(intent)
    }

    private fun deleteLocation(location: Location) {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val locationHandler = LocationHandler(dataBaseHandler)

        locationHandler.deleteLocation(location.id)
        Thread {
            displayLocationsList(getLocations())
        }.run()
    }
}