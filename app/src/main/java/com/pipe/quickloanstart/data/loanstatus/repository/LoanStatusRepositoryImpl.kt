package com.pipe.quickloanstart.data.loanstatus.repository

import com.pipe.quickloanstart.data.loanstatus.api.LoanStatusApi
import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.LoanStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoanStatusRepositoryImpl @Inject constructor(
    private val loanStatusApi: LoanStatusApi,
) : LoanStatusRepository {

    override suspend fun invoke(idRequest: Int)
            : Flow<BaseResult<LoanEntity, Throwable>> {
        return flow {
            val response = loanStatusApi.login(idRequest)
            val registerEntity = LoanEntity(
                firstName = response.firstName,
                lastName = response.lastName,
                phoneNumber = response.phoneNumber,
                amount = response.amount,
                percent = response.percent,
                period = response.period,
                date = response.date,
                state = response.state,
                id = response.id
            )
            emit(BaseResult.Success(registerEntity))
        }
    }
}