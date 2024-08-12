package com.financesbox.core.event

interface EventListener<E : Event> {

    fun receive(event: E)

}