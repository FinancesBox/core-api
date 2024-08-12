package com.financesbox.core.micronaut.event.fakes

import com.financesbox.core.event.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class SecondFakeEventListener(
    @Inject
    private val service: FakeService
) : EventListener<FakeEvent> {

    override fun receive(event: FakeEvent) {
        service.doSomething(event.name)
    }

}