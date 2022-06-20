package com.pipe.quickloanstart.domain.usecases

import com.pipe.quickloanstart.data.models.RegistrationEntity
import com.pipe.quickloanstart.data.models.RegistrationRequest
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    suspend fun invoke(registrationRequest: RegistrationRequest)
            : Flow<BaseResult<RegistrationEntity, Throwable>> {
        return registrationRepository.invoke(registrationRequest)
    }
}