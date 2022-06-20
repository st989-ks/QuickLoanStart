package com.pipe.quickloanstart.data.loanslist.repository

import com.pipe.quickloanstart.data.loanslist.api.LoansListApi
import com.pipe.quickloanstart.data.loanslist.cashlists.ActionFile
import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.di.BaseResult
import com.pipe.quickloanstart.domain.repository.LoansListRepository
import com.pipe.quickloanstart.extensions.SharedPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoansListRepositoryImpl @Inject constructor(
    private val loansListApi: LoansListApi,
    private val sharedPrefs: SharedPrefs,
    private val actionFile: ActionFile
) : LoansListRepository {


    override suspend fun invoke(): Flow<BaseResult<List<LoanEntity>, Throwable>> {
        return flow {
            val response: List<LoanEntity>

            if (sharedPrefs.checkTenThousandMillisecond()){
                response = loansListApi.response()
                actionFile.updateFile(response as MutableList<LoanEntity>)
            }else{
                response = actionFile.readContactsFiles()
            }
            emit(BaseResult.Success(response))
        }
    }
}

