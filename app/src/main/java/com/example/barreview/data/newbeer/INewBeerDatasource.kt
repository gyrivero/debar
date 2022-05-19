package com.example.barreview.data.newbeer

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface INewBeerDatasource {
    suspend fun addBeer(id: String, color: String, name: String, brand: String, rating: Float)
}