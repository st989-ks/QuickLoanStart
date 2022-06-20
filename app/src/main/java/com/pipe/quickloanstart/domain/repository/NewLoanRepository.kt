package com.pipe.quickloanstart.domain.repository

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.data.models.NewLoanRequest
import com.pipe.quickloanstart.di.BaseResult
import kotlinx.coroutines.flow.Flow

interface NewLoanRepository {
    suspend fun invoke(newLoanRequest: NewLoanRequest):
            Flow<BaseResult<LoanEntity, Throwable>>
}