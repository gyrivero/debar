package com.example.barreview.viewmodel.bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barreview.domain.bar.IBarRepo
import com.example.barreview.domain.barlist.IBarListRepo

class BarViewModelFactory (val repo: IBarRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IBarRepo::class.java).newInstance(repo)
    }
}