package com.example.barreview.domain.barlist

import com.example.barreview.data.barlist.IBarListDatasource
import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

class BarListRepo (private val repo: IBarListDatasource) : IBarListRepo {
    override suspend fun fetchBarList(): Resource<MutableList<Bar>> = repo.fetchBarList()

    override suspend fun deleteBar(id: String) {
        repo.deleteBar(id)
    }

}