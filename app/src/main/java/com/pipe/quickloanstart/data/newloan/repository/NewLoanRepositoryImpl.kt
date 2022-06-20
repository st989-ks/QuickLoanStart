package com.pipe.quickloanstart.data.newloan.repository

import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.data.models.NewLoanRequest
import com.pipe.quickloanstart.data.newloan.api.NewLoanApi
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.NewLoanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class NewLoanRepositoryImpl @Inject constructor(
    private val newLoanApi: NewLoanApi,
) : NewLoanRepository {

    override suspend fun invoke(newLoanRequest: NewLoanRequest)
            : Flow<BaseResult<LoanEntity, Throwable>> {
        return flow {

            if (newLoanRequest.amount > 0) {

                val response = newLoanApi.login(newLoanRequest)
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
            } else {
                emit(
                    BaseResult.Errors(
                        HttpException(
                            Response.error<ResponseBody>(
                                500, ResponseBody.create(
                                    MediaType.parse("plain/text"), "some content"
                                )
                            )
                        )
                    )
                )
            }
        }
    }
}