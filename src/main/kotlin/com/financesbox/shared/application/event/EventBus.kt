package com.financesbox.shared.application.event

import com.financesbox.shared.domain.event.Event

interface EventBus {

    /**
     * Publishes an event
     * @param event Event to publish
     */
    suspend fun publish(event: Event)

}