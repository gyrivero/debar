package com.example.barreview.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.barreview.domain.auth.IAuthRepo
import com.example.barreview.domain.newbar.INewBarRepo
import com.example.barreview.domain.newbar.NewBarRepo
import com.example.barreview.domain.newbeer.INewBeerRepo
import com.example.barreview.util.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: IAuthRepo) : ViewModel() {


    fun signIn(email : String,password : String) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}