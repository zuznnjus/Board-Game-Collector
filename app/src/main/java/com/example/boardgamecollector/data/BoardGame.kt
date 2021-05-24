package com.example.boardgamecollector.data

import java.time.LocalDate

data class BoardGame(
    var id: Int? = 0,
    var bggId: Int? = 0,
    var title: String? = "",
    var originalTitle: String?,
    var yearPublished: Int? = null,
    var description: String? = null,
    var orderDate: LocalDate = LocalDate.now(),
    var addedToCollectionDate: LocalDate = LocalDate.now(),
    var price: Double? = 0.0,
    var suggestedRetailPrice: Double? = 0.0,
    var eanOrUpc: String? = "",
    var productionCode: String? = "",
    var type: String?,
    var comment: String? = null,
    var thumbnail: String? = null
) {
    override fun toString(): String {
        return "$originalTitle ($yearPublished)\n $description"
    }
}