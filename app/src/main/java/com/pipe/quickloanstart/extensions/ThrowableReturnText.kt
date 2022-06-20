package com.pipe.quickloanstart.extensions

import com.pipe.quickloanstart.R
import retrofit2.HttpException

class ThrowableReturnText {

    fun foundThrowable(throwable: Throwable): Int {

        return when (throwable) {
            is HttpException -> when (throwable.code()) {
                400 -> R.string.invalid_request_400
                401 -> R.string.incorrect_authorization_401
                403 -> R.string.no_access_to_the_resource_403
                404 -> R.string.resource_is_missing_404
                405 -> R.string.method_not_allowed_405
                500 -> R.string.enter_the_amount_500
                else -> R.string.unknown_error_0
            }
            else -> R.string.unknown_error_0
        }
    }
}

