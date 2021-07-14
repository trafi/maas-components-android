package com.trafi.sample

import com.trafi.core.ApiResult
import com.trafi.sample.SampleAction.DecrementButtonTapped
import com.trafi.sample.SampleAction.FactAlertDismissed
import com.trafi.sample.SampleAction.IncrementButtonTapped
import com.trafi.sample.SampleAction.NumberFactButtonTapped
import com.trafi.sample.SampleAction.NumberFactResponse
import com.trafi.state.Effect
import com.trafi.state.State
import com.trafi.state.effect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

data class SampleState(
    var count: Int,
    var numberFactAlert: String?,
) : State<SampleState, SampleAction, SampleEnvironment> {
    override fun reduce(
        action: SampleAction,
        environment: SampleEnvironment,
    ): Pair<SampleState, Effect<SampleAction>> = when (action) {
        FactAlertDismissed -> {
            copy(numberFactAlert = null) to Effect.None()
        }
        DecrementButtonTapped -> {
            copy(count = this.count - 1) to Effect.None()
        }
        IncrementButtonTapped -> {
            copy(count = this.count + 1) to Effect.None()
        }
        NumberFactButtonTapped -> {
            this to environment.fetchNumberFact(count)
        }
        is NumberFactResponse -> {
            copy(
                numberFactAlert = action.result.value ?: "Could not load a number fact"
            ) to Effect.None()
        }
    }
}

sealed class SampleAction {
    object FactAlertDismissed : SampleAction()
    object DecrementButtonTapped : SampleAction()
    object IncrementButtonTapped : SampleAction()
    object NumberFactButtonTapped : SampleAction()
    class NumberFactResponse(val result: ApiResult<String>) : SampleAction()
}

class SampleEnvironment {
    var fetchNumberFact: (count: Int) -> Effect<SampleAction> = { count ->
        flow<SampleAction> {
            delay(1000)
            emit(NumberFactResponse(ApiResult.Success("Number $count is great")))
        }.effect("fetch-number-fact", cancelInFlight = true)
    }
}

private val <T : Any> ApiResult<T>.value: T? get() = (this as? ApiResult.Success)?.value
