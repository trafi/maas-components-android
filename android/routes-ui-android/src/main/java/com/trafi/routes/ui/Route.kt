package com.trafi.routes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trafi.core.model.Route
import com.trafi.core.model.RouteDisruption
import com.trafi.core.model.RouteSegment
import com.trafi.core.model.RouteSegmentPersonalVehicle
import com.trafi.core.model.mockRoute
import com.trafi.routes.ui.internal.endTimeMillis
import com.trafi.routes.ui.internal.startTimeMillis
import com.trafi.ui.theme.MaasTheme
import java.text.DateFormat
import java.util.Date
import java.util.TimeZone

@Composable
public fun Route(route: Route, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                contentDescription = route.accessibilityLabel
            }
            .clickable(onClick = onClick)
    ) {
        Cell(
            modifier = Modifier
                .defaultMinSize(minHeight = 64.dp)
                .padding(horizontal = MaasTheme.spacing.globalMargin, vertical = 12.dp),
            content = {
                Row {
                    route.segments.forEachIndexed { index, segment ->
                        RouteSegment(
                            segment,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        if (index != route.segments.lastIndex) {
                            Spacer(modifier = Modifier.size(4.dp))
                        }
                    }
                }
                route.disruption?.let { disruption ->
                    disruption.title?.let { disruptionTitle ->
                        val vector = ImageVector.vectorResource(
                            when (disruption.severity) {
                                RouteDisruption.Severity.NOT_AFFECTED,
                                RouteDisruption.Severity.INFORMATION -> R.drawable.warning_info_s
                                RouteDisruption.Severity.WARNING -> R.drawable.warning_warning_s
                                RouteDisruption.Severity.ALERT -> R.drawable.warning_alert_s
                            }
                        )
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Image(
                                vector,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                disruptionTitle,
                                style = MaasTheme.typography.textM
                            )
                        }
                    }
                }
            },
            suffix = {
                Text(
                    route.duration.text,
                    modifier = Modifier.align(Alignment.End),
                    style = MaasTheme.typography.textL.copy(fontWeight = FontWeight.Bold),
                )
                route.fare?.total?.text?.let {
                    Text(
                        it,
                        modifier = Modifier.align(Alignment.End),
                        style = MaasTheme.typography.textM
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun RoutePreview() {
    Route(mockRoute, onClick = {})
}

@Composable
private fun Cell(
    modifier: Modifier = Modifier,
    prefix: (@Composable ColumnScope.() -> Unit)? = null,
    suffix: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(modifier) {
        prefix?.let { Column { it() } }
        Column(modifier = Modifier.weight(1f)) { content() }
        suffix?.let { Column { it() } }
    }
}

private val Route.accessibilityLabel: String get() {
    val costLabel = listOfNotNull(duration.text, fare?.total?.text).joinToString(separator = " ")
    val segmentsLabel = segments.mapNotNull { it.accessibilityLabel }.joinToString(separator = ", ")
    val disruptionLabel = disruption?.title
    val timeLabel =
        "leave at ${formatShortTime(startTimeMillis)} arrive at ${formatShortTime(endTimeMillis)}"
    return listOfNotNull(costLabel, segmentsLabel, disruptionLabel, timeLabel)
        .joinToString(separator = ", ", postfix = ".")
}

private val RouteSegment.accessibilityLabel: String? get() {
    val minutes = maxOf((endTimeMillis - startTimeMillis).millisToMins, 1)
    return when (mode) {
        RouteSegment.Mode.TRANSIT -> transit?.schedule?.run {
            listOfNotNull(transportType, name).joinToString(separator = " ")
        }
        RouteSegment.Mode.RIDE_HAILING -> rideHailing?.provider?.name
        RouteSegment.Mode.SHARING -> sharing?.provider?.name
        RouteSegment.Mode.WALKING -> "Walk $minutes min"
        RouteSegment.Mode.PERSONAL_VEHICLE -> when (personalVehicle?.vehicle) {
            RouteSegmentPersonalVehicle.Vehicle.BICYCLE -> "Ride bicycle $minutes min"
            RouteSegmentPersonalVehicle.Vehicle.KICK_SCOOTER -> "Ride kick-scooter $minutes min"
            null -> null
        }
    }
}

private val Long.millisToMins: Long get() = this / 1000 / 60

/**
 * HH:mm
 */
private fun formatShortTime(millis: Long) = DateFormat.getTimeInstance(DateFormat.SHORT).run {
    timeZone = TimeZone.getTimeZone("UTC")
    format(Date(millis))
}
