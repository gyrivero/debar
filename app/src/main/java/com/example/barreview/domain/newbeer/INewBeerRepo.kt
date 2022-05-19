package com.example.barreview.domain.newbeer

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface INewBeerRepo {

    suspend fun addBeer(id: String, color: String, name: String, brand: String, rating: Float)

}