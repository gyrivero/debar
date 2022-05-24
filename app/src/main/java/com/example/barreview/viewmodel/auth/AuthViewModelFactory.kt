package com.example.barreview.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barreview.domain.auth.IAuthRepo
import com.example.barreview.domain.barlist.IBarListRepo
import com.example.barreview.domain.newbar.INewBarRepo
import com.example.barreview.domain.newbeer.INewBeerRepo

class AuthViewModelFactory (val repo: IAuthRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IAuthRepo::class.java).newInstance(repo)
    }
}