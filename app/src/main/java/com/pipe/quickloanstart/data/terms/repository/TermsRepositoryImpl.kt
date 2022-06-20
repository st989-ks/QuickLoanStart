package com.pipe.quickloanstart.data.terms.repository

import com.pipe.quickloanstart.data.models.TermsEntity
import com.pipe.quickloanstart.data.terms.api.TermsApi
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.TermsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TermsRepositoryImpl @Inject constructor(
    private val termsApi: TermsApi,
) : TermsRepository {

    override suspend fun invoke(): Flow<BaseResult<TermsEntity, Throwable>> {
        return flow {
            val response = termsApi.response()
            emit(BaseResult.Success(response))
        }
    }
}

