package com.pipe.quickloanstart.data.newloan

import com.pipe.quickloanstart.data.newloan.api.NewLoanApi
import com.pipe.quickloanstart.data.newloan.repository.NewLoanRepositoryImpl
import com.pipe.quickloanstart.domain.repository.NewLoanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class NewLoanModule {

    @Provides
    fun provideApiNewLoan(
        retrofit: Retrofit,
    ): NewLoanApi {
        return retrofit.create(NewLoanApi::class.java)
    }

    @Provides
    fun bindNewLoanDataSource(
        api: NewLoanApi
    ): NewLoanRepository =
        NewLoanRepositoryImpl(api)
}