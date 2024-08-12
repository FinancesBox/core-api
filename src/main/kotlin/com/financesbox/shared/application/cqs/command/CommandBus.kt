package com.financesbox.shared.application.cqs.command

import com.financesbox.shared.domain.event.Event
import kotlinx.coroutines.Deferred

interface CommandBus {

    /**
     * Executes the command asynchronously.
     *
     * @param command The command to execute.
     * @return A Future representing the event published.
     */
    fun <E : Event, C : Command<E>> asyncExecute(command: C): Deferred<E>

    /**
     * Executes the command synchronously, blocking until the result is available.
     *
     * @param command The command to execute.
     * @return The event published.
     */
    suspend fun <E : Event, C : Command<E>> syncExecute(command: C): E

}