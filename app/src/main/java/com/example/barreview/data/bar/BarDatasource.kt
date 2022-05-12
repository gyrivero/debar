package com.example.barreview.data.bar

import android.util.Log
import com.example.barreview.model.Bar
import com.example.barreview.model.Beer
import com.example.barreview.model.FoodReview
import com.example.barreview.util.DbConstants
import com.example.barreview.util.Resource
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await


class BarDatasource : IBarDatasource {

    override suspend fun getBar(id: String): Resource<Bar> {
        val doc = FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).get().await()
        val bar = Bar(id,doc.getString(DbConstants.NAME),doc.getString(DbConstants.ADDRESS),doc.getString(DbConstants.NEIGHBORHOOD),
            doc.getDouble(DbConstants.RATING)?.toFloat(),doc.getDate(DbConstants.CREATED_AT))
        return Resource.Success(bar)
    }

    override suspend fun fetchFoodReviewList(id: String): Resource<MutableList<FoodReview>> {
        val list = mutableListOf<FoodReview>()
        val querySnapshot = FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).collection(DbConstants.FOOD).get().await()
        for (document in querySnapshot.documents) {
            val foodReview = FoodReview(
                document.getDouble(DbConstants.RATING)?.toFloat(),
                document.getDate(DbConstants.CREATED_AT)
            )
            list.add(foodReview)
            //list.add(document.toObject(Bar::class.java))
        }

        return Resource.Success(list)
    }

    override suspend fun addFoodReview(id: String, rating: Float) {
        val data = hashMapOf(
            DbConstants.RATING to rating.toDouble(),
            DbConstants.CREATED_AT to Timestamp(System.currentTimeMillis()/1000.toLong(),0)
        )
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).collection(DbConstants.FOOD).add(data).await()
    }

}