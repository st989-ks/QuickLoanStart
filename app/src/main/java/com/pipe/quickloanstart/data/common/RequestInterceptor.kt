package com.pipe.quickloanstart.data.common

import com.pipe.quickloanstart.extensions.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
    private val sharedPrefs: SharedPrefs,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefs.getToken()
        val newRequest = chain
            .request()
            .newBuilder().apply {
                if (token.isNotEmpty()) {
                    addHeader("accept", "*/*")
                    addHeader("Content-Type", "application/json")
                    addHeader("Authorization", token)
                } else {
                    addHeader("accept", "*/*")
                    addHeader("Content-Type", "application/json")
                }
            }.build()

        return chain.proceed(newRequest)
    }
}