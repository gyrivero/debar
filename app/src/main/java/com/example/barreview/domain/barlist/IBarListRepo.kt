package com.example.barreview.domain.barlist

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface IBarListRepo {
    suspend fun fetchBarList(): Resource<MutableList<Bar>>

    suspend fun deleteBar(id : String)
}