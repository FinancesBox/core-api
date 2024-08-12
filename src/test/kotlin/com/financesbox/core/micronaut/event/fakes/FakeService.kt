package com.financesbox.core.micronaut.event.fakes

import io.kotest.mpp.log
import jakarta.inject.Singleton

@Singleton
open class FakeService {

    open fun doSomething(name: String) {
        log { "Hello ${name}!" }
    }

}