package com.example.barreview.data.newbar

import com.example.barreview.model.Bar
import com.example.barreview.util.DbConstants
import com.example.barreview.util.Resource
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class NewBarDatasource : INewBarDatasource {

    override suspend fun addBar(name : String,address : String,neighborhood : String) {
        val data = hashMapOf(
            DbConstants.NAME to name,
            DbConstants.ADDRESS to address,
            DbConstants.NEIGHBORHOOD to neighborhood,
            DbConstants.RATING to 0f.toDouble(),
            DbConstants.BEERRATING to 0f.toDouble(),
            DbConstants.FOODRATING to 0f.toDouble(),
            DbConstants.CREATED_AT to Timestamp(System.currentTimeMillis()/1000.toLong(),0)
        )
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).add(data).await()
    }



}