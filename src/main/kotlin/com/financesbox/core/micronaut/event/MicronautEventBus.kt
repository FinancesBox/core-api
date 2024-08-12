package com.financesbox.core.micronaut.event

import com.financesbox.core.event.Event
import com.financesbox.core.event.EventBus
import io.micronaut.context.annotation.Requires
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Requires(property = "event.implementation", value = "MICRONAUT")
class MicronautEventBus(
    @Inject private val broker: MicronautEventBroker
) : EventBus {

    override suspend fun publish(event: Event) {
        broker.dispatch(event)
    }

}