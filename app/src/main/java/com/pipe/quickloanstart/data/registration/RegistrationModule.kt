package com.pipe.quickloanstart.data.registration

import com.pipe.quickloanstart.data.registration.api.RegistrationApi
import com.pipe.quickloanstart.data.registration.repository.RegistrationRepositoryImpl
import com.pipe.quickloanstart.domain.repository.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class RegistrationModule {

    @Provides
    fun provideRegistrationApi(
        retrofit: Retrofit,
    ): RegistrationApi {
        return retrofit.create(RegistrationApi::class.java)
    }

    @Provides
    fun bindRegistrationDataSource(
        api: RegistrationApi
    ): RegistrationRepository =
        RegistrationRepositoryImpl(api)
}