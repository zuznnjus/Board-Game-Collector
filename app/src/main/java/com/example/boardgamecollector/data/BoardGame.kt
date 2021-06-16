package com.example.boardgamecollector.data

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

data class BoardGame(
    var id: Int = 0,
    var bggId: Int = 0,
    var title: String? = "",
    var originalTitle: String? = null,
    var yearPublished: Int? = null,
    var description: String? = null,
    var orderDate: LocalDate? = LocalDate.now(),
    var addedToCollectionDate: LocalDate? = LocalDate.now(),
    var price: Double? = 0.0,
    var suggestedRetailPrice: Double? = 0.0,
    var eanOrUpc: String? = "",
    var productionCode: String? = "",
    var type: String? = "boardgame",
    var comment: String? = null,
    var thumbnail: String? = null,

    var designers: List<Designer> = listOf(),
    var artists: List<Artist> = listOf(),
    var ranking: Int? = null,
    var location: Location? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        bggId = parcel.readInt(),
        originalTitle = parcel.readString()
    )

    override fun toString(): String {
        return "$originalTitle ($yearPublished)\n"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bggId)
        parcel.writeString(originalTitle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoardGame> {
        override fun createFromParcel(parcel: Parcel): BoardGame {
            return BoardGame(parcel)
        }

        override fun newArray(size: Int): Array<BoardGame?> {
            return arrayOfNulls(size)
        }
    }
}