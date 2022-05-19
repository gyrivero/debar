package com.example.barreview.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class FoodReview(val rating: Float?, @ServerTimestamp val created_at: Date?) {     constructor() : this( null,null) {}

}