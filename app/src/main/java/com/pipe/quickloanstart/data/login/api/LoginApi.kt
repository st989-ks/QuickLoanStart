package com.pipe.quickloanstart.data.login.api

import com.pipe.quickloanstart.data.models.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): String
}