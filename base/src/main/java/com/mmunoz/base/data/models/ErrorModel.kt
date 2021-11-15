package com.mmunoz.base.data.models

import com.mmunoz.base.R
import retrofit2.HttpException

object ExceptionCodes {
    const val ERROR_UNKNOWN = 0
    const val ERROR_NOT_FOUND = 404
    const val ERROR_CONNECTION = 502
    const val ERROR_BAD_REQUEST = 400
    const val ERROR_SERVER_ERROR = 503
    const val ERROR_SERVICE_ERROR = 401
}

fun Throwable.getErrorMessage(): Int {
    return when (getHttpErrorCode()) {
        ExceptionCodes.ERROR_NOT_FOUND -> R.string.error_not_found
        ExceptionCodes.ERROR_CONNECTION -> R.string.error_connection
        ExceptionCodes.ERROR_BAD_REQUEST -> R.string.error_bad_request
        ExceptionCodes.ERROR_SERVER_ERROR -> R.string.error_server_error
        ExceptionCodes.ERROR_SERVICE_ERROR -> R.string.error_service_error
        else -> R.string.error_unknown
    }
}

private fun Throwable.getHttpErrorCode(): Int {
    return (this as? HttpException)?.code() ?: ExceptionCodes.ERROR_UNKNOWN
}