package com.pipe.quickloanstart.domain.repository

import com.pipe.quickloanstart.data.models.LoginRequest
import com.pipe.quickloanstart.data.models.TokenEntity
import com.pipe.quickloanstart.di.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun invoke(loginRequest: LoginRequest)
            : Flow<BaseResult<TokenEntity, Throwable>>
}