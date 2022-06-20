package com.pipe.quickloanstart.domain.usecases

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.LoanStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoanStatusUseCase @Inject constructor(
    private val loanStatusRepository: LoanStatusRepository
) {
    suspend fun invoke(idRequest: Int)
            : Flow<BaseResult<LoanEntity, Throwable>> {
        return loanStatusRepository.invoke(idRequest)
    }
}