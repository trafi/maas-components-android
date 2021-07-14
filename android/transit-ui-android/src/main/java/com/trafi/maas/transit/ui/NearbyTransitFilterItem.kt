package com.trafi.maas.transit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trafi.core.model.ext.FilterItem
import com.trafi.core.model.mock.mockFilters
import com.trafi.maas.transit.ui.internal.NearbyTransitFilterItemConstants
import com.trafi.ui.component.MultiSelectFilter
import com.trafi.ui.component.SingleSelectFilter
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.currentTheme
import com.trafi.ui.theme.internal.type.toColor
import com.trafi.ui.theme.isRound

private val constants
    @Composable get() = NearbyTransitFilterItemConstants(currentTheme)

@Composable
internal fun NearbyTransitFilterItem(
    item: FilterItem,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.sizeIn(
            minWidth = constants.itemMinWidth,
            minHeight = constants.itemMinHeight
        )
    ) {
        Surface(
            shape = if (constants.cornerRadius.isRound) {
                RoundedCornerShape(percent = 50)
            } else {
                RoundedCornerShape(constants.cornerRadius)
            },
            color = if (enabled) item.color.toColor() else constants.disabledColor,
            modifier = Modifier
                .sizeIn(
                    minWidth = constants.contentMinWidth,
                    minHeight = constants.contentMinHeight
                )
        ) {
            val vector = ImageVector.vectorResource(when (item.icon) {
                "ubahn" -> R.drawable.providers_ubahn_xs
                "sbahn" -> R.drawable.providers_sbahn_xs
                "bus" -> R.drawable.providers_bus_xs
                "tram" -> R.drawable.providers_trams_xs
                "train" -> R.drawable.providers_train_xs
                "ferry" -> R.drawable.providers_ferry_xs
                else -> R.drawable.providers_bus_xs
            })
            Image(
                imageVector = vector,
                contentDescription = null,
                modifier = Modifier
                    .width(constants.imageWidth)
                    .height(constants.imageHeight),
                colorFilter = ColorFilter.tint(color = item.accentColor.toColor())
            )
        }
    }
}

@Preview
@Composable
private fun MultiFilterPreview() {
    val filters = mockFilters
    val startPadding = Modifier.padding(8.dp)
    Column {
        Text(
            text = "Multi select",
            style = MaasTheme.typography.headingM,
            modifier = startPadding,
        )
        Text(
            text = "Deselected",
            style = MaasTheme.typography.textS,
            modifier = startPadding,
        )
        MultiSelectFilter(
            items = filters,
            enabledItems = emptyList(),
            onItemClick = { },
        ) { item, enabled ->
            NearbyTransitFilterItem(
                item = item,
                enabled = enabled,
            )
        }
        Text(
            text = "Half selected",
            style = MaasTheme.typography.textS,
            modifier = startPadding,
        )
        MultiSelectFilter(
            items = filters,
            enabledItems = filters.take(3),
            onItemClick = { },
        ) { item, enabled ->
            NearbyTransitFilterItem(
                item = item,
                enabled = enabled,
            )
        }
        Text(
            text = "All selected",
            style = MaasTheme.typography.textS,
            modifier = startPadding,
        )
        MultiSelectFilter(
            items = filters,
            enabledItems = filters,
            onItemClick = { },
        ) { item, enabled ->
            NearbyTransitFilterItem(
                item = item,
                enabled = enabled,
            )
        }
    }
}

@Preview
@Composable
private fun SingleFilterPreview() {
    val filters = mockFilters
    val startPadding = Modifier.padding(8.dp)
    Column {
        Text(
            text = "Single select",
            style = MaasTheme.typography.headingM,
            modifier = startPadding,
        )
        SingleSelectFilter(
            items = filters,
            enabledItem = filters.first(),
            onItemClick = { },
        ) { item, enabled ->
            NearbyTransitFilterItem(item = item, enabled = enabled)
        }
    }
}
