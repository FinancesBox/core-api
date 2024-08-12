package com.financesbox.core.micronaut.event

import com.financesbox.core.event.EventBus
import com.financesbox.core.micronaut.event.fakes.FakeEvent
import com.financesbox.core.micronaut.event.fakes.FakeService
import io.kotest.core.spec.style.FunSpec
import io.micronaut.context.annotation.Property
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.*
import jakarta.inject.Inject

@MicronautTest
@Property(name = "event.implementation", value = "MICRONAUT")
class MicronautEventBusSpec(
    @Inject private val eventBus: EventBus,
    @Inject private val service: FakeService
) : FunSpec({

    test("should publish an event and call a service") {
        val event = FakeEvent("fake")
        every { service.doSomething(any()) } just Runs

        eventBus.publish(event)

        verify(exactly = 2) {
            service.doSomething(any())
        }
    }

}) {

    @MockBean(FakeService::class)
    fun fakeService(): FakeService {
        return mockk<FakeService>()
    }

}
