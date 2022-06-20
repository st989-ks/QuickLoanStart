package com.pipe.quickloanstart.data.loanslist.api

import com.pipe.quickloanstart.data.models.LoanEntity
import retrofit2.http.GET

interface LoansListApi {

    @GET("/loans/all")
    suspend fun response(): List<LoanEntity>
}