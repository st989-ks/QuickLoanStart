package com.pipe.quickloanstart.data.registration.repository

import com.pipe.quickloanstart.data.models.RegistrationEntity
import com.pipe.quickloanstart.data.models.RegistrationRequest
import com.pipe.quickloanstart.data.registration.api.RegistrationApi
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val registrationApi: RegistrationApi,
) : RegistrationRepository {

    override suspend fun invoke(registrationRequest: RegistrationRequest)
            : Flow<BaseResult<RegistrationEntity, Throwable>> {
        return flow {
            val response = registrationApi.registration(registrationRequest)
            val registerEntity = RegistrationEntity(response.name, response.role)
            emit(BaseResult.Success(registerEntity))
        }
    }
}

