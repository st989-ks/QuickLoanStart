package com.pipe.quickloanstart.domain.usecases

import com.pipe.quickloanstart.data.models.LoginRequest
import com.pipe.quickloanstart.data.models.TokenEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun invoke(loginRequest: LoginRequest)
            : Flow<BaseResult<TokenEntity, Throwable>> {
        return loginRepository.invoke(loginRequest)
    }
}