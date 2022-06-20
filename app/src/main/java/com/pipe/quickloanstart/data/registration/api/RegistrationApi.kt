package com.pipe.quickloanstart.data.registration.api

import com.pipe.quickloanstart.data.models.RegistrationEntity
import com.pipe.quickloanstart.data.models.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {

    @POST("registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): RegistrationEntity
}