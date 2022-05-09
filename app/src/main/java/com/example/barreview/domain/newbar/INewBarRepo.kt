package com.example.barreview.domain.newbar

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface INewBarRepo {

    suspend fun addBar(name : String,address : String,neighborhood : String)
}