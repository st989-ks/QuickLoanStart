package com.pipe.quickloanstart.domain.usecases

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.LoansListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoansListUseCase @Inject constructor(
    private val loansListRepository: LoansListRepository
) {
    suspend fun invoke()
            : Flow<BaseResult<List<LoanEntity>, Throwable>> {
        return loansListRepository.invoke()
    }
}