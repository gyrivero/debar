package com.example.barreview.domain.bar

import com.example.barreview.model.Bar
import com.example.barreview.model.Beer
import com.example.barreview.model.FoodReview
import com.example.barreview.util.Resource

interface IBarRepo {
    suspend fun getBar(id : String): Resource<Bar>

    suspend fun fetchFoodReviewList(id: String) : Resource<MutableList<FoodReview>>

    suspend fun addFoodReview(id: String,rating: Float)

    suspend fun fetchBeerList(id: String) : Resource<MutableList<Beer>>

    suspend fun updateBarRating(id: String,gRating: Float,fRating: Float ,bRating: Float)

    suspend fun deleteBeer(idBar:String, idBeer: String)

}