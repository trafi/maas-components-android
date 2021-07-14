package com.trafi.routes.ui.internal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trafi.core.model.AutoCompleteLocation
import com.trafi.core.model.Location
import com.trafi.routes.ui.mock.vilniusCathedral
import com.trafi.ui.theme.MaasTheme

@Composable
internal fun LocationsResult(
    result: List<AutoCompleteLocation>,
    onClick: (AutoCompleteLocation) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(result) { location ->
            LocationResult(
                location,
                onClick = { onClick(location) },
                modifier = Modifier.fillParentMaxWidth()
            )
            if (result.indexOf(location) != result.lastIndex) {
                Divider(
                    modifier = Modifier
                        .padding(horizontal = MaasTheme.spacing.globalMargin)
                )
            }
        }
    }
}

@Composable
private fun LocationResult(
    location: AutoCompleteLocation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = LocationResult(location.name, location.address, onClick, modifier)

@Composable
private fun LocationResult(
    location: Location,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = LocationResult(location.name ?: location.coordinate.toString(), null, onClick, modifier)

@Composable
private fun LocationResult(
    name: String,
    address: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .defaultMinSize(minHeight = 60.dp)
                .padding(horizontal = MaasTheme.spacing.globalMargin, vertical = 12.dp)
        ) {
            Icon(
                Icons.Outlined.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    name,
                    style = MaasTheme.typography.textL.copy(fontWeight = FontWeight.SemiBold),
                )
                address?.let {
                    Text(
                        it,
                        style = MaasTheme.typography.textM.copy(color = MaasTheme.colors.grayScale.gray500),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationResultPreview() {
    MaasTheme {
        LocationResult(location = vilniusCathedral, onClick = {})
    }
}
