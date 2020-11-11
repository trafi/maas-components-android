package com.trafi.routes.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trafi.core.android.model.AutoCompleteLocation
import com.trafi.core.android.model.Location
import com.trafi.locations.android.LocationsApi
import com.trafi.routes.android.RoutesApi
import com.trafi.routes.ui.internal.LocationSearchResultState
import com.trafi.routes.ui.internal.LocationSearchViewModel
import com.trafi.routes.ui.internal.LocationsResult
import com.trafi.routes.ui.internal.RouteSearchHeader
import com.trafi.routes.ui.internal.RoutesResultState
import com.trafi.routes.ui.internal.RoutesViewModel
import com.trafi.routes.ui.internal.displayText
import com.trafi.routes.ui.mock.vilniusAirport
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.Spacing

@Composable
fun RoutesScreen(
    baseUrl: String,
    apiKey: String,
    regionId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    initialStart: Location? = vilniusAirport,
    initialEnd: Location? = null,
) {
    val factory = ViewModelFactory(baseUrl, apiKey, regionId)
    val routesViewModel: RoutesViewModel = viewModel(factory = factory)
    val locationViewModel: LocationSearchViewModel = viewModel(factory = factory)

    var searchingForStart by remember { mutableStateOf(false) }
    var searchingForEnd by remember { mutableStateOf(false) }

    var start: Location? by remember { mutableStateOf(initialStart) }
    var end: Location? by remember { mutableStateOf(initialEnd) }

    var startText by remember { mutableStateOf(initialStart.displayText) }
    var endText by remember { mutableStateOf(initialEnd.displayText) }

    var softwareKeyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }

    fun tryRouteSearch() {
        start?.let { start -> end?.let { end -> routesViewModel.search(start, end) } }
    }

    Column(
        modifier = modifier
            .background(MaasTheme.colors.background)
            .fillMaxHeight()
    ) {
        RouteSearchHeader(
            originText = startText,
            destinationText = endText,
            onOriginTextChange = {
                searchingForEnd = false
                endText = end.displayText

                startText = it
                searchingForStart = true
                locationViewModel.search(it)
            },
            onDestinationTextChange = {
                searchingForStart = false
                startText = start.displayText

                endText = it
                searchingForEnd = true
                locationViewModel.search(it)
            },
            onTextInputStarted = { keyboardController ->
                softwareKeyboardController = keyboardController
            },
            onSwitchClick = {
                searchingForEnd = false
                searchingForStart = false
                val prevStart = start
                start = end
                end = prevStart

                startText = start.displayText
                endText = end.displayText
                tryRouteSearch()
            },
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaasTheme.spacing.globalMargin)
        )

        if (searchingForStart || searchingForEnd) {
            LocationSearchBody(
                state = locationViewModel.state,
                modifier = Modifier
                    .padding(top = Spacing.md),
                onClick = { location ->
                    softwareKeyboardController?.hideSoftwareKeyboard()
                    locationViewModel.resolveLocation(location)
                }
            )
            locationViewModel.resolvedLocation?.let { location ->
                when {
                    searchingForStart -> {
                        start = location
                        startText = location.displayText
                        tryRouteSearch()
                        searchingForStart = false
                        locationViewModel.resolvedLocationHandled()
                    }
                    searchingForEnd -> {
                        end = location
                        endText = location.displayText
                        tryRouteSearch()
                        searchingForEnd = false
                        locationViewModel.resolvedLocationHandled()
                    }
                }
            }
        } else {
            RouteSearchBody(
                state = routesViewModel.state,
                modifier = Modifier.padding(top = Spacing.md)
            )
        }
    }
}

@Composable
private fun ColumnScope.RouteSearchBody(
    state: RoutesResultState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        RoutesResultState.NoResults -> {
            Text(
                "No results",
                modifier = modifier.align(Alignment.CenterHorizontally),
                style = MaasTheme.typography.textL
            )
        }
        RoutesResultState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
        is RoutesResultState.Loaded -> RoutesResult(state.result, modifier)
    }
}

@Composable
private fun ColumnScope.LocationSearchBody(
    state: LocationSearchResultState,
    onClick: (AutoCompleteLocation) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        LocationSearchResultState.NoResults -> {
            Text(
                "No locations found",
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                style = MaasTheme.typography.textL
            )
        }
        LocationSearchResultState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        is LocationSearchResultState.Loaded -> LocationsResult(state.result, onClick, modifier)
    }
}

private class ViewModelFactory(baseUrl: String, apiKey: String, regionId: String) :
    ViewModelProvider.Factory {

    private val locationsApi: LocationsApi by lazy { LocationsApi(baseUrl, apiKey, regionId) }
    private val routesApi: RoutesApi by lazy { RoutesApi(baseUrl, apiKey) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RoutesViewModel::class.java -> RoutesViewModel(routesApi) as T
            LocationSearchViewModel::class.java -> LocationSearchViewModel(locationsApi) as T
            else -> throw IllegalArgumentException("Unexpected ViewModel $modelClass")
        }
    }
}
