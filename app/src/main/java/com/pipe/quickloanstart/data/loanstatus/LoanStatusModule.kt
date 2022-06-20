package com.pipe.quickloanstart.data.loanstatus

import com.pipe.quickloanstart.data.loanstatus.api.LoanStatusApi
import com.pipe.quickloanstart.data.loanstatus.repository.LoanStatusRepositoryImpl
import com.pipe.quickloanstart.domain.repository.LoanStatusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class LoanStatusModule {

    @Provides
    fun provideApiLoanStatus(
        retrofit: Retrofit,
    ): LoanStatusApi {
        return retrofit.create(LoanStatusApi::class.java)
    }

    @Provides
    fun bindLoanStatusDataSource(
        api: LoanStatusApi
    ): LoanStatusRepository =
        LoanStatusRepositoryImpl(api)
}