package com.financesbox.core.micronaut.cqs.command

import com.financesbox.core.cqs.command.Command
import com.financesbox.core.cqs.command.CommandBus
import com.financesbox.core.event.Event
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.*

@Singleton
@OptIn(DelicateCoroutinesApi::class)
@Requires(property = "command.implementation", value = "MICRONAUT")
class MicronautCommandBus(
    @Value("\${micronaut.command.timeoutInMillis}") private val timeout: Long,
    @Inject private val registry: MicronautCommandRegistry
) : CommandBus {

    override fun <E : Event, C : Command<E>> asyncExecute(command: C): Deferred<E> = GlobalScope.async {
        registry.getHandler(command).handle(command)
    }

    override suspend fun <E : Event, C : Command<E>> syncExecute(command: C): E {
        return withTimeout(timeout) {
            asyncExecute(command).await()
        }
    }

}