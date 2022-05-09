package com.example.barreview.domain.bar

import com.example.barreview.data.barlist.IBarListDatasource
import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

class BarRepo (private val repo: IBarListDatasource) : IBarRepo {
}