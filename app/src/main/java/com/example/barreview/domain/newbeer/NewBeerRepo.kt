package com.example.barreview.domain.newbeer

import com.example.barreview.data.newbar.INewBarDatasource
import com.example.barreview.data.newbeer.INewBeerDatasource

class NewBeerRepo (private val data: INewBeerDatasource) : INewBeerRepo {

    override suspend fun addBeer(id: String, color: String, name: String, brand: String, rating: Float) = data.addBeer(id, color, name, brand, rating)

}