package com.pipe.quickloanstart.domain.repository

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoansListRepository {
    suspend fun invoke()
            : Flow<BaseResult<List<LoanEntity>, Throwable>>
}