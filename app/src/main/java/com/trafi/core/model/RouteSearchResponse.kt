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
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @param id
 * @param now
 * @param routes
 * @param groups
 */
@Parcelize

data class RouteSearchResponse(
    @Json(name = "id")
    val id: kotlin.String,
    @Json(name = "now")
    val now: kotlin.String,
    @Json(name = "routes")
    val routes: kotlin.collections.List<Route>,
    @Json(name = "groups")
    val groups: kotlin.collections.List<Group>? = null
) : Parcelable
