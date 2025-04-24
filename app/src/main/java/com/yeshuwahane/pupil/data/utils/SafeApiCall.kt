package com.yeshuwahane.pupil.data.utils

import retrofit2.Response


suspend inline fun <T, R> safeApiCall(
    crossinline apiCall: suspend () -> T,
    crossinline mapper: (T) -> R
): DataResource<R> {
    return try {
        val response = apiCall()
        DataResource.success(mapper(response))
    } catch (e: Exception) {
        DataResource.error(e, null)
    }
}
