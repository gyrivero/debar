package com.example.barreview.viewmodel.newbeer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.barreview.domain.newbar.INewBarRepo
import com.example.barreview.domain.newbar.NewBarRepo
import com.example.barreview.domain.newbeer.INewBeerRepo
import com.example.barreview.util.Resource
import kotlinx.coroutines.Dispatchers

class NewBeerViewModel(private val repo: INewBeerRepo) : ViewModel() {


    fun addBeer(id: String, color: String, name: String, brand: String, rating: Float) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.addBeer(id, color, name, brand, rating)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}