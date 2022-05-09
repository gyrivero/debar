package com.example.barreview.data.newbar

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface INewBarDatasource {
    suspend fun addBar(name : String,address : String,neighborhood : String)
}