package com.example.barreview.viewmodel.barlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barreview.domain.barlist.IBarListRepo

class BarListViewModelFactory (val repo: IBarListRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IBarListRepo::class.java).newInstance(repo)
    }
}