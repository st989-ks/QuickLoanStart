package com.pipe.quickloanstart.data.loanslist

import com.pipe.quickloanstart.data.loanslist.api.LoansListApi
import com.pipe.quickloanstart.data.loanslist.cashlists.ActionFile
import com.pipe.quickloanstart.data.loanslist.repository.LoansListRepositoryImpl
import com.pipe.quickloanstart.domain.repository.LoansListRepository
import com.pipe.quickloanstart.extensions.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class LoansListModule {

    @Provides
    fun provideLoansListApi(
        retrofit: Retrofit,
    ): LoansListApi {
        return retrofit.create(LoansListApi::class.java)
    }

    @Provides
    fun bindLoansListDataSource(
        api: LoansListApi,
        sharedPrefs: SharedPrefs,
        actionFile: ActionFile
    ): LoansListRepository =
        LoansListRepositoryImpl(api,sharedPrefs,actionFile)
}