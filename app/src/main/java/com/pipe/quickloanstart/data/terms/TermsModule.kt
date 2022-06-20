package com.pipe.quickloanstart.data.terms

import com.pipe.quickloanstart.data.terms.api.TermsApi
import com.pipe.quickloanstart.data.terms.repository.TermsRepositoryImpl
import com.pipe.quickloanstart.domain.repository.TermsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class TermsModule {

    @Provides
    fun provideTermsApi(
        retrofit: Retrofit,
    ): TermsApi {
        return retrofit.create(TermsApi::class.java)
    }

    @Provides
    fun bindTermsDataSource(
        api: TermsApi
    ): TermsRepository =
        TermsRepositoryImpl(api)
}