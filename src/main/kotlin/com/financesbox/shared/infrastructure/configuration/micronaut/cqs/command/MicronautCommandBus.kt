package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command

import com.financesbox.shared.application.cqs.command.Command
import com.financesbox.shared.application.cqs.command.CommandBus
import com.financesbox.shared.domain.event.Event
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.memberProperties

@Singleton
@OptIn(DelicateCoroutinesApi::class)
@Requires(property = "command.implementation", value = "MICRONAUT")
class MicronautCommandBus(
    @Value("\${micronaut.command.timeoutInMillis}") private val timeout: Long,
    private val registry: MicronautCommandRegistry,
    private val logger: Logger = LoggerFactory.getLogger(MicronautCommandBus::class.java),
) : CommandBus {

    override fun <E : Event, C : Command<E>> asyncExecute(command: C): Deferred<E> = GlobalScope.async {
        logger.info("Executing ${command::class.simpleName}: [${
            command::class.memberProperties.joinToString(separator = ", ") { property ->
                "${property.name}=${property.getter.call(command)}"
            }
        }]")
        registry.getHandler(command).handle(command)
    }

    override fun <E : Event, C : Command<E>> syncExecute(command: C): E {
        return runBlocking {
            withTimeout(timeout) {
                asyncExecute(command).await()
            }
        }
    }

}