package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command

import com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command.fakes.FakeCommand
import com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command.fakes.FakeEvent
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import kotlin.time.measureTime

@MicronautTest
@Property(name = "command.implementation", value = "MICRONAUT")
@Property(name = "micronaut.command.timeoutInMillis", value = "1000")
class MicronautCommandBusTest(
    private val commandBus: MicronautCommandBus,
) : FunSpec({

    test("should execute asynchronously a command and return an event") {
        val delay = 1000L
        val command = FakeCommand("fake", delay)
        var event: FakeEvent
        val time = measureTime {
            event = commandBus.asyncExecute(command).await()
        }
        time.inWholeSeconds shouldBe delay / 1000
        event.name shouldBe command.name
    }

    test("should execute synchronously a command and return an event") {
        val command = FakeCommand("fake", 0)
        val event = commandBus.syncExecute(command)
        event.name shouldBe command.name
    }

})
