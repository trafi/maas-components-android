/**
* Trafi platform API
* Introduction ===  Trafi Platform API is a comprehensive Mobility as a Service API which allows to retrieve information and consume different mobility types, perform multi modal route search, create and manage users and perform transactions.  Conceptually, API consists of the following parts: - Public transit information - Multi modal route search - Vehicle sharing integrations - Users - Payments  Below you will find description of each part in detail.  Public transit information ---  On a high level, public transit information in Trafi consists of these items:   - Schedules (in the industry commonly referred to as routes or lines) - Stops and stations with metro exits - Timetables and departures (in the industry commonly referred to as schedules) - Pricing information (used in route search engine to calculate fare of the trip)  Public transit API allows you to:  - Get information about particular schedule (also known as line or route) with all its tracks and stops (for API details go to [SchedulesV1](#operations-tag-SchedulesV1)) - Get a list of public transit stops within a specified region or geographic bounds (for API details go to [StopsV1](#operations-tag-StopsV1)) - Get a list of nearest static (based on the timetable) or real-time (if available) departures including information about the schedule and direction for a particular stop (for API details go to [StopsV1](#operations-tag-StopsV1))  Multi modal route search ---  Trafi route search is a flexible engine which combines data from a broad range of transport options and 3rd party on demand integrations in order to provide live data, fares etc. where available. This enables routes to combine different modes of transport based on accurate, complete and up to date information for accurate comparison of the transport options in a multi modal route.  Route search engine calculates a set of metrics per route, with a parameter per route attribute, such that when combined with the criteria, a route is assigned a score per criteria set. This allows routes to be quantitatively assessed on their various parameters such as cost, walk distance, duration, transfer count etc. and be returned to user sorted by most recommended option.  Routing API allows you to:  - Get routes for given start and end coordinates (for API details go to [RoutesV1](#operations-tag-RoutesV1)) - Get walking path for given start and end coordinates (for API details go to [DirectionsV1](#operations-tag-DirectionsV1))  Vehicle sharing integrations ---  Comming soon...  Users ---  Comming soon...  Payments ---  Comming soon...
*
* The version of the OpenAPI document: v1
* 
*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package com.trafi.core.model

import com.trafi.core.model.ParkingRecord
import com.trafi.core.model.RideHailingRecord
import com.trafi.core.model.SharingRecord
import com.trafi.core.model.SubscriptionRenewalRecord
import com.trafi.core.model.TicketActivationsRecord
import com.trafi.core.model.TicketsRecord

import com.squareup.moshi.Json
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 
 * @param rideHailing 
 * @param sharing 
 * @param tickets 
 * @param ticketActivations 
 * @param parking 
 * @param subscriptionRenewals 
 */
@Parcelize

data class Records (
    @Json(name = "rideHailing")
    val rideHailing: kotlin.collections.List<RideHailingRecord>,
    @Json(name = "sharing")
    val sharing: kotlin.collections.List<SharingRecord>,
    @Json(name = "tickets")
    val tickets: kotlin.collections.List<TicketsRecord>,
    @Json(name = "ticketActivations")
    val ticketActivations: kotlin.collections.List<TicketActivationsRecord>,
    @Json(name = "parking")
    val parking: kotlin.collections.List<ParkingRecord>,
    @Json(name = "subscriptionRenewals")
    val subscriptionRenewals: kotlin.collections.List<SubscriptionRenewalRecord>
) : Parcelable

