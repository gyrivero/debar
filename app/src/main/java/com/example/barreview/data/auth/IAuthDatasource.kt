package com.example.barreview.data.auth

import com.example.barreview.model.Bar
import com.example.barreview.util.Resource

interface IAuthDatasource {
    suspend fun signIn(email : String,password : String)
}