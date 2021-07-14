package com.trafi.routes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trafi.core.model.Provider
import com.trafi.core.model.RouteSegment
import com.trafi.core.model.RouteSegmentPersonalVehicle
import com.trafi.core.model.Schedule
import com.trafi.core.model.SharedVehicle
import com.trafi.routes.ui.internal.endTimeMillis
import com.trafi.routes.ui.internal.startTimeMillis
import com.trafi.routes.ui.mock.RouteSegmentPreviewParameterProvider
import com.trafi.ui.component.Badge
import com.trafi.ui.component.BadgeInfo
import com.trafi.ui.component.BadgeType
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.internal.type.toColor

private typealias PersonalVehicleType = RouteSegmentPersonalVehicle.Vehicle

@Composable
public fun RouteSegment(segment: RouteSegment, modifier: Modifier = Modifier) {
    when (segment.mode) {
        RouteSegment.Mode.TRANSIT -> {
            val transit = segment.transit ?: return
            val badge = transit.schedule.toBadgeInfo()
            val alternativeBadges = transit.alternatives.map { it.schedule.toBadgeInfo() }
            val vector = ImageVector.vectorResource(
                when (transit.schedule.transportType) {
                    "ubahn" -> R.drawable.providers_ubahn_xs
                    "sbahn" -> R.drawable.providers_sbahn_xs
                    "bus" -> R.drawable.providers_bus_xs
                    "tram" -> R.drawable.providers_trams_xs
                    "train" -> R.drawable.providers_train_xs
                    "ferry" -> R.drawable.providers_ferry_xs
                    else -> R.drawable.providers_bus_xs
                }
            )
            Badge(
                badge = badge,
                alternativeBadges = alternativeBadges,
                icon = vector,
                badgeType = BadgeType.Medium,
                modifier = modifier
            )
        }
        RouteSegment.Mode.RIDE_HAILING -> {
            val hailing = segment.rideHailing ?: return
            val badge = hailing.provider?.toBadgeInfo()
            val vector = ImageVector.vectorResource(
                when (hailing.provider?.icon) {
                    "berlkonig" -> R.drawable.providers_berlkonig_xs
                    else -> R.drawable.transport_taxi_xs
                }
            )
            badge?.let {
                Badge(
                    badge = it,
                    icon = vector,
                    badgeType = BadgeType.Medium,
                    modifier = modifier
                )
            }
        }
        RouteSegment.Mode.SHARING -> {
            val sharing = segment.sharing ?: return
            val badge = sharing.provider?.toBadgeInfo()
            val providerIconRes = when (sharing.provider?.icon) {
                "tier" -> R.drawable.providers_tier_xs
                "voi" -> R.drawable.providers_voi_xs
                "emmy" -> R.drawable.providers_emmy_xs
                "nextbike" -> R.drawable.providers_nextbike_xs
                "driveby" -> R.drawable.providers_driveby_xs
                else -> null
            }
            val transportIconRes = sharing.vehicle?.vehicleType?.let { vehicleType ->
                when (vehicleType) {
                    SharedVehicle.VehicleType.CAR -> R.drawable.transport_car_xs
                    SharedVehicle.VehicleType.BICYCLE -> R.drawable.transport_bike_xs
                    SharedVehicle.VehicleType.SCOOTER -> R.drawable.transport_scooter_xs
                    SharedVehicle.VehicleType.KICK_SCOOTER -> R.drawable.transport_kickscooter_xs
                }
            }
            val vector = (providerIconRes ?: transportIconRes)?.let { ImageVector.vectorResource(it) }
            badge?.let {
                Badge(
                    badge = it,
                    icon = vector,
                    badgeType = BadgeType.Medium,
                    modifier = modifier
                )
            }
        }
        RouteSegment.Mode.WALKING -> {
            val vector = ImageVector.vectorResource(R.drawable.ic_route_search_walking_s)
            val durationMillis = segment.endTimeMillis - segment.startTimeMillis
            Row(modifier = modifier.defaultMinSize(minHeight = 24.dp)) {
                Image(
                    vector,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    // colorFilter = ColorFilter.tint(AmbientContentColor.current)
                )
                Text(
                    text = durationMillis.millisToDurationText,
                    style = MaasTheme.typography.textS.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
        }
        RouteSegment.Mode.PERSONAL_VEHICLE -> {
            val personalVehicle = segment.personalVehicle ?: return
            val vector = ImageVector.vectorResource(
                when (personalVehicle.vehicle) {
                    PersonalVehicleType.BICYCLE -> R.drawable.ic_route_search_bike_s
                    PersonalVehicleType.KICK_SCOOTER -> R.drawable.ic_route_search_scooter_s
                }
            )
            val durationMillis = segment.endTimeMillis - segment.startTimeMillis
            Row(modifier = modifier.defaultMinSize(minHeight = 24.dp)) {
                Image(
                    vector,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    // colorFilter = ColorFilter.tint(AmbientContentColor.current)
                )
                Text(
                    text = durationMillis.millisToDurationText,
                    style = MaasTheme.typography.textS.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
        }
    }
}

private fun Schedule.toBadgeInfo(): BadgeInfo = BadgeInfo(
    text = name,
    backgroundColor = color.toColor(),
    contentColor = textColor?.toColor()
)

private fun Provider.toBadgeInfo(): BadgeInfo = BadgeInfo(
    text = name,
    backgroundColor = color?.toColor() ?: Color.Black
)

private val Long.millisToDurationText: String
    get() = maxOf(1, ((this / 1000 + 30) / 60)).toString()

@Preview(showBackground = false)
@Composable
private fun RouteSegmentPreview(
    @PreviewParameter(RouteSegmentPreviewParameterProvider::class) segment: RouteSegment
) {
    RouteSegment(segment)
}
