package com.trafi.ondemandSharing

import com.trafi.core.ApiConfiguration
import com.trafi.core.ApiResult
import com.trafi.core.ConfiguredApi
import com.trafi.core.api
import com.trafi.core.model.CreateSharingBookingRequest
import com.trafi.core.model.SharingBooking
import io.ktor.client.*
import io.ktor.client.request.*

class OndemandSharingApi internal constructor(
    config: ApiConfiguration,
    private val httpClient: HttpClient,
) : ConfiguredApi by config.api() {

    suspend fun createOndemandBooking(request: CreateSharingBookingRequest): ApiResult<SharingBooking> = try {
        val result = httpClient.authorized.post<SharingBooking>(baseApiUrl + "v1/ondemand/sharing/bookings") {
            body = request
        }
        ApiResult.Success(result)
    } catch (e: Throwable) {
        ApiResult.ktorFailure(e)
    }
}