package com.financesbox.shared.application.event

import com.financesbox.shared.domain.event.Event

interface EventListener<E : Event> {

    fun receive(event: E)

}