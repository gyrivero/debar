package com.example.barreview.data.barlist

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface IBarListDatasource {
    suspend fun fetchBarList(): Resource<MutableList<Bar>>

    suspend fun deleteBar(id:String)
}