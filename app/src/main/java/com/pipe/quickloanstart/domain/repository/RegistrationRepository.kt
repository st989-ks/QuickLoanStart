package com.pipe.quickloanstart.domain.repository

import com.pipe.quickloanstart.data.models.RegistrationEntity
import com.pipe.quickloanstart.data.models.RegistrationRequest
import com.pipe.quickloanstart.di.BaseResult
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun invoke(registrationRequest: RegistrationRequest)
            : Flow<BaseResult<RegistrationEntity, Throwable>>
}