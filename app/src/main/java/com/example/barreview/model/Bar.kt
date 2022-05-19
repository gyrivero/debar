package com.example.barreview.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Bar(
    val id: String?, val name: String?, val address: String?, val neighborhood:String?, var generalRating: Float?, var beerRating: Float?, var foodRating: Float?, @ServerTimestamp var created_at: Date?){
    constructor() : this( null,null, null, null, null,null, null, null) {}
}
