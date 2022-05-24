package com.example.barreview.domain.auth

import com.example.barreview.data.auth.IAuthDatasource
import com.example.barreview.data.newbeer.INewBeerDatasource

class AuthRepo (private val data: IAuthDatasource) : IAuthRepo {
    override suspend fun signIn(email: String, password: String) = data.signIn(email, password)
}