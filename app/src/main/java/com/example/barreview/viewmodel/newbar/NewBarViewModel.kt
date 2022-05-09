package com.example.barreview.viewmodel.newbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.barreview.domain.newbar.INewBarRepo
import com.example.barreview.domain.newbar.NewBarRepo
import com.example.barreview.util.Resource
import kotlinx.coroutines.Dispatchers

class NewBarViewModel(private val repo: INewBarRepo) : ViewModel() {


    fun addBar(name : String,address : String,neighborhood : String) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.addBar(name,address,neighborhood)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}