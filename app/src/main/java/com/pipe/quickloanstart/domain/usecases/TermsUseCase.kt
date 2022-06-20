package com.pipe.quickloanstart.domain.usecases

import com.pipe.quickloanstart.data.models.TermsEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.TermsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TermsUseCase @Inject constructor(
    private val termsRepository: TermsRepository
) {
    suspend fun invoke()
            : Flow<BaseResult<TermsEntity, Throwable>> {
        return termsRepository.invoke()
    }
}