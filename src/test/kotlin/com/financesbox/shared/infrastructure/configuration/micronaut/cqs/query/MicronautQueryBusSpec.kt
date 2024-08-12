package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.query

import com.financesbox.shared.infrastructure.configuration.micronaut.cqs.query.fakes.FakeQuery
import com.financesbox.shared.infrastructure.configuration.micronaut.cqs.query.fakes.FakeQueryModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import kotlin.time.measureTime

@MicronautTest
@Property(name = "query.implementation", value = "MICRONAUT")
@Property(name = "micronaut.query.timeoutInMillis", value = "1000")
class MicronautQueryBusSpec(
    @Inject private val commandBus: MicronautQueryBus
) : FunSpec({

    test("should execute asynchronously a query and return an event") {
        val delay = 1000L
        val query = FakeQuery("fake", delay)
        var event: FakeQueryModel
        val time = measureTime {
            event = commandBus.asyncExecute(query).await()
        }
        time.inWholeSeconds shouldBe delay / 1000
        event.name shouldBe query.name
    }

    test("should execute synchronously a query and return an event") {
        val query = FakeQuery("fake", 0)
        val event = commandBus.syncExecute(query)
        event.name shouldBe query.name
    }

})
