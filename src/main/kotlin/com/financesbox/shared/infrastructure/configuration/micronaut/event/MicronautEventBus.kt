package com.financesbox.shared.infrastructure.configuration.micronaut.event

import com.financesbox.shared.application.event.EventBus
import com.financesbox.shared.domain.event.Event
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(property = "event.implementation", value = "MICRONAUT")
class MicronautEventBus(
    private val broker: MicronautEventBroker
) : EventBus {

    override suspend fun publish(event: Event) {
        broker.dispatch(event)
    }

}