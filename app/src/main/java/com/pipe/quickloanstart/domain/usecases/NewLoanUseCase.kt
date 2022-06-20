package com.pipe.quickloanstart.domain.usecases

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.data.models.NewLoanRequest
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.NewLoanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewLoanUseCase @Inject constructor(
    private val newLoanRepository: NewLoanRepository
) {
    suspend fun invoke(newLoanRequest: NewLoanRequest)
            : Flow<BaseResult<LoanEntity, Throwable>> {
        return newLoanRepository.invoke(newLoanRequest)
    }
}