package com.pipe.quickloanstart.data.login

import com.pipe.quickloanstart.data.login.api.LoginApi
import com.pipe.quickloanstart.data.login.repository.LoginRepositoryImpl
import com.pipe.quickloanstart.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideApiLogin(
        retrofit: Retrofit,
    ): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun bindLoginDataSource(
        api: LoginApi
    ): LoginRepository =
        LoginRepositoryImpl(api)
}