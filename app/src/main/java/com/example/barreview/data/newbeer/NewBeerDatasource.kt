package com.example.barreview.data.newbeer

import com.example.barreview.util.DbConstants
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class NewBeerDatasource : INewBeerDatasource {

    override suspend fun addBeer(id: String, color: String, name: String, brand: String,rating: Float) {
        val data = hashMapOf(
            DbConstants.COLOR to color,
            DbConstants.NAME to name,
            DbConstants.BRAND to brand,
            DbConstants.RATING to rating,
            DbConstants.CREATED_AT to Timestamp(System.currentTimeMillis()/1000.toLong(),0)
        )
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).collection(DbConstants.BEERS).add(data).await()
    }

}