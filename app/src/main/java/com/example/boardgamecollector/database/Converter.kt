package com.example.boardgamecollector.database

import java.time.LocalDate

object Converter {
    fun convertLocalDateToEpochDay(localDate: LocalDate) : Long {
        return localDate.toEpochDay()
    }

    fun convertEpochDayToLocalDate(epochDay: Long) : LocalDate {
        return LocalDate.ofEpochDay(epochDay)
    }
}