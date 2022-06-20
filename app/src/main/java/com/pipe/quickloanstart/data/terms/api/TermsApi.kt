package com.pipe.quickloanstart.data.terms.api

import com.pipe.quickloanstart.data.models.TermsEntity
import retrofit2.http.GET

interface TermsApi {

    @GET("/loans/conditions")
    suspend fun response(): TermsEntity
}