package com.pipe.quickloanstart.data.newloan.api

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.data.models.NewLoanRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface NewLoanApi {

    @POST("/loans")
    suspend fun login(@Body newLoanRequest: NewLoanRequest): LoanEntity
}