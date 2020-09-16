package com.trafi.example

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.tooling.preview.Preview
import com.trafi.core.ApiResult
import com.trafi.core.model.LatLng
import com.trafi.core.model.Location
import com.trafi.core.model.RoutesResult
import com.trafi.example.ui.MaasTheme
import com.trafi.routes.RoutesApi
import com.trafi.routes.ui.RoutesResult
import kotlinx.coroutines.launch

@Composable
fun DemoApp() {
    val viewModel: DemoViewModel = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "maas-components-android")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Favorite)
                    }
                }
            )
        }) { innerPadding ->
        BodyContent(
            start = viewModel.start,
            end = viewModel.end,
            state = viewModel.routesResultState,
            onSearchClick = { viewModel.search() },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun BodyContent(
    start: Location?,
    end: Location?,
    state: RoutesResultState,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        start?.let { Waypoint(it, Modifier.padding(16.dp)) }
        end?.let {
            Waypoint(
                it, Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (start == null) 16.dp else 0.dp
                )
            )
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            enabled = state !is RoutesResultState.Loading,
            onClick = { onSearchClick() }) {
            Text("Search")
        }
        when (state) {
            RoutesResultState.NoResults -> {
                Text(
                    "No results",
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)
                )
            }
            RoutesResultState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)
                )
            }
            is RoutesResultState.Loaded -> RoutesResult(state.result)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaasTheme {
        BodyContent(
            start = pragueTechMuseum,
            end = pragueCastle,
            state = RoutesResultState.NoResults,
            onSearchClick = {})
    }
}

@Composable
fun Waypoint(location: Location, modifier: Modifier = Modifier) {
    Row(modifier) {
        Icon(
            Icons.Filled.Place,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(location.name
            ?: location.address
            ?: location.coordinate.run { "$lat,$lng" })
    }
}

sealed class RoutesResultState {
    object NoResults : RoutesResultState()
    object Loading : RoutesResultState()
    data class Loaded(val result: RoutesResult) : RoutesResultState()
}

class DemoViewModel : ViewModel() {

    private val routesApi = RoutesApi(baseUrl = BuildConfig.API_BASE_URL)

    var start: Location by mutableStateOf(pragueTechMuseum)
    var end: Location by mutableStateOf(pragueCastle)

    var routesResultState: RoutesResultState by mutableStateOf(RoutesResultState.NoResults)
        private set

    fun search() {
        routesResultState = RoutesResultState.Loading
        viewModelScope.launch {
            val result = routesApi.search(start, end)
            routesResultState = when (result) {
                is ApiResult.Success -> RoutesResultState.Loaded(result.value)
                is ApiResult.Failure -> RoutesResultState.NoResults
            }
        }
    }
}

@Stable
private val pragueTechMuseum = Location(
    coordinate = LatLng(50.096943, 14.425130),
    name = "National Technical Museum",
    address = "Kostelní 1320/42, 170 00 Praha 7-Letná, Czechia"
)

@Stable
private val pragueCastle = Location(
    coordinate = LatLng(50.064356, 14.420030),
    name = "Vyšehrad",
    address = "V Pevnosti 159/5b, 128 00 Praha 2-Vyšehrad, Czechia"
)