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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class BarDatasource : IBarDatasource {

    override suspend fun getBar(id: String): Resource<Bar> {
        val doc = FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).get().await()
        val bar = Bar(id,doc.getString(DbConstants.NAME),doc.getString(DbConstants.ADDRESS),doc.getString(DbConstants.NEIGHBORHOOD),
            doc.getDouble(DbConstants.RATING)?.toFloat(),doc.getDouble(DbConstants.BEERRATING)?.toFloat(),doc.getDouble(DbConstants.FOODRATING)?.toFloat(),doc.getDate(DbConstants.CREATED_AT))
        return Resource.Success(bar)
    }

    override suspend fun fetchFoodReviewList(id: String): Resource<MutableList<FoodReview>> {
        val list = mutableListOf<FoodReview>()
        val querySnapshot = FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).collection(DbConstants.FOOD).orderBy(DbConstants.CREATED_AT,Query.Direction.DESCENDING).get().await()
        for (document in querySnapshot.documents) {
            val foodReview = FoodReview(
                document.getDouble(DbConstants.RATING)?.toFloat(),
                document.getDate(DbConstants.CREATED_AT)
            )
            list.add(foodReview)
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

    override suspend fun fetchBeerList(id: String): Resource<MutableList<Beer>> {
        val list = mutableListOf<Beer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).collection(DbConstants.BEERS).orderBy(DbConstants.CREATED_AT,Query.Direction.DESCENDING).get().await()
        for (document in querySnapshot.documents) {
            val beer = Beer(document.id,document.getString(DbConstants.COLOR),document.getString(DbConstants.NAME),document.getString(DbConstants.BRAND),
                document.getDouble(DbConstants.RATING)?.toFloat(),document.getDate(DbConstants.CREATED_AT))
            list.add(beer)
        }

        return Resource.Success(list)
    }

    override suspend fun updateBarRating(id: String,gRating: Float,fRating: Float ,bRating: Float) {
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).update(mapOf(DbConstants.RATING to gRating,DbConstants.FOODRATING to fRating,DbConstants.BEERRATING to bRating)).await()

    }

    override suspend fun deleteBeer(idBar : String,idBeer: String) {
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(idBar).collection(DbConstants.BEERS).document(idBeer).delete().await()

    }


}