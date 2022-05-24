package com.example.barreview.domain.auth

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface IAuthRepo {
    suspend fun signIn(email : String,password : String)
}