package com.trafi.ondemandSharing

import com.trafi.core.ApiResult
import com.trafi.core.CFlow
import com.trafi.core.cFlow
import com.trafi.core.model.CreateSharingBookingRequest
import com.trafi.core.model.SharingBooking

fun OndemandSharingApi.createOndemandBooking(request: CreateSharingBookingRequest): CFlow<ApiResult<SharingBooking>> =
    cFlow { emit(createOndemandBooking(request)) }
