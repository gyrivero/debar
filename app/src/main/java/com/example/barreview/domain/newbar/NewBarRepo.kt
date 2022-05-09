package com.example.barreview.domain.newbar

import com.example.barreview.data.barlist.IBarListDatasource
import com.example.barreview.data.newbar.INewBarDatasource
import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

class NewBarRepo (private val data: INewBarDatasource) : INewBarRepo {

    override suspend fun addBar(name : String,address : String,neighborhood : String) = data.addBar(name,address,neighborhood)
}