package com.example.barreview.viewmodel.bar

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.barreview.domain.bar.IBarRepo
import com.example.barreview.domain.barlist.IBarListRepo
import com.example.barreview.model.Bar
import com.example.barreview.model.FoodReview
import com.example.barreview.ui.BarFragment
import com.example.barreview.ui.ContainerActivity
import com.example.barreview.util.Resource
import kotlinx.coroutines.Dispatchers

class BarViewModel(private val repo: IBarRepo) : ViewModel() {

    fun getBar(id : String) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val bar = repo.getBar(id)
            emit(bar)
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchFoodReviewList(id : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val list = repo.fetchFoodReviewList(id)
            emit(list)
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun addFoodReview(id: String, rating: Float) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.addFoodReview(id,rating)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}