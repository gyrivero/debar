package com.example.barreview.viewmodel.newbeer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barreview.domain.barlist.IBarListRepo
import com.example.barreview.domain.newbar.INewBarRepo
import com.example.barreview.domain.newbeer.INewBeerRepo

class NewBeerViewModelFactory (val repo: INewBeerRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(INewBeerRepo::class.java).newInstance(repo)
    }
}