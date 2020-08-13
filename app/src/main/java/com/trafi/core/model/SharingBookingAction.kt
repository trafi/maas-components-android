/**
* Core Whitelabel API
* No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
*
* The version of the OpenAPI document: v1
*
*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package com.trafi.core.model

import com.squareup.moshi.Json

/**
*
* Values: RESERVE,LEASE,FINISH,CANCEL,PAUSE
*/

enum class SharingBookingAction(val value: kotlin.String) {

    @Json(name = "RESERVE")
    RESERVE("RESERVE"),

    @Json(name = "LEASE")
    LEASE("LEASE"),

    @Json(name = "FINISH")
    FINISH("FINISH"),

    @Json(name = "CANCEL")
    CANCEL("CANCEL"),

    @Json(name = "PAUSE")
    PAUSE("PAUSE");
}
