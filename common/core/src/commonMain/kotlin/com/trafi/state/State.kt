@file:Suppress("unused")

package com.trafi.state

import com.trafi.core.CFlow
import com.trafi.core.wrap
import kotlinx.coroutines.flow.Flow

interface State<S : State<S, Action, Environment>, Action : Any, Environment> {
    fun reduce(action: Action, environment: Environment): Pair<S, Effect<Action>>
}

sealed class Effect<T : Any> {
    class None<T : Any> : Effect<T>()

    class Cancel<T : Any>(val id: String) : Effect<T>()

    class Flow<T : Any>(
        val flow: CFlow<T>,
        val cancellationId: String? = null,
        val cancelInFlight: Boolean = true,
    ) : Effect<T>()
}

class Effects<T : Any>(val effects: List<Effect<T>>) : Effect<T>()

internal fun <T : Any> Flow<T>.effect(
    cancellationId: String,
    cancelInFlight: Boolean = false,
): Effect<T> = Effect.Flow(
    flow = wrap(),
    cancellationId = cancellationId,
    cancelInFlight = cancelInFlight,
)

internal fun <T : Any> Flow<T>.effect(): Effect<T> = Effect.Flow(wrap())
