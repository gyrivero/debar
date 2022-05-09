package com.example.barreview.viewmodel.newbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barreview.domain.barlist.IBarListRepo
import com.example.barreview.domain.newbar.INewBarRepo

class NewBarViewModelFactory (val repo: INewBarRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(INewBarRepo::class.java).newInstance(repo)
    }
}