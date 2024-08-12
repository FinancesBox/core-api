package com.financesbox.core.event

interface EventBus {

    /**
     * Publishes an event
     * @param event Event to publish
     */
    suspend fun publish(event: Event)

}