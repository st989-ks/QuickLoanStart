package com.pipe.quickloanstart.domain.repository

import com.pipe.quickloanstart.data.models.TermsEntity
import com.pipe.quickloanstart.di.BaseResult
import kotlinx.coroutines.flow.Flow

interface TermsRepository {
    suspend fun invoke():
            Flow<BaseResult<TermsEntity, Throwable>>
}