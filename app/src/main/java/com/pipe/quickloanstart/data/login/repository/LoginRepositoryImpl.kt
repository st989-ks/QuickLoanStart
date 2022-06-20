package com.pipe.quickloanstart.data.login.repository

import com.pipe.quickloanstart.data.login.api.LoginApi
import com.pipe.quickloanstart.data.models.LoginRequest
import com.pipe.quickloanstart.data.models.TokenEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
) : LoginRepository {

    override suspend fun invoke(loginRequest: LoginRequest)
            : Flow<BaseResult<TokenEntity, Throwable>> {
        return flow {
            val response = loginApi.login(loginRequest)
            val registerEntity = TokenEntity(response)
            emit(BaseResult.Success(registerEntity))
        }
    }
}