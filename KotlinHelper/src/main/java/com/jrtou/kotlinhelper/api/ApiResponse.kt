package com.jrtou.kotlinhelper.api


import retrofit2.Response
import java.net.UnknownServiceException

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        private const val TAG = "ApiLiveDataResponse"

        /**
         * 建立 請求異常回傳結果
         */
        fun <T> create(error: Throwable): ApiErrorResponse<T> = ApiErrorResponse(error)

        /**
         * 建立請求成功回傳結果
         */
        fun <T> create(response: Response<T>): ApiResponse<T> =
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) ApiEmptyResponse()
                else ApiSuccessResponse(body)
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg
                ApiErrorResponse(UnknownServiceException(errorMsg), response.code())
            }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val throwable: Throwable, val code: Int = 404) : ApiResponse<T>()
