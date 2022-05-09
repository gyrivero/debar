package com.example.barreview.viewmodel.barlist

import androidx.lifecycle.*
import com.example.barreview.domain.barlist.IBarListRepo
import com.example.barreview.util.Resource
import kotlinx.coroutines.Dispatchers

class BarListViewModel(private val repo: IBarListRepo) : ViewModel() {


    val fetchBarList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val list = repo.fetchBarList()
            emit(list)
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun deleteBar(id : String) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.deleteBar(id)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }



}