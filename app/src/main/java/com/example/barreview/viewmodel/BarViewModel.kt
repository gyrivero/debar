package com.example.barreview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barreview.repository.Repo
import com.example.barreview.model.Bar

class BarViewModel : ViewModel() {

    private val repo = Repo()

    fun fetchBarData():LiveData<MutableList<Bar>> {
        val mutableList = MutableLiveData<MutableList<Bar>>()
        repo.getData().observeForever{
            mutableList.value = it
        }
        return mutableList
    }

    fun addBar() {
        repo.addBar()
    }

}