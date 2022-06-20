package com.pipe.quickloanstart.data.loanstatus.api

import com.pipe.quickloanstart.data.models.LoanEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface LoanStatusApi {

    @GET("/loans/{id}")
    suspend fun login(@Path("id") id: Int): LoanEntity
}