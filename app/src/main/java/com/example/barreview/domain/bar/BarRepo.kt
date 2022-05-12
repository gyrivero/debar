package com.example.barreview.domain.bar

import com.example.barreview.data.bar.IBarDatasource
import com.example.barreview.model.Bar
import com.example.barreview.model.FoodReview
import com.example.barreview.util.Resource

class BarRepo (private val data: IBarDatasource) : IBarRepo {
    override suspend fun getBar(id: String): Resource<Bar> = data.getBar(id)

    override suspend fun fetchFoodReviewList(id: String): Resource<MutableList<FoodReview>> = data.fetchFoodReviewList(id)

    override suspend fun addFoodReview(id: String, rating: Float) = data.addFoodReview(id,rating)
}