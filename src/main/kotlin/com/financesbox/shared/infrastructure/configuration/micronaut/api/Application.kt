package com.financesbox.shared.infrastructure.configuration.micronaut.api

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.slf4j.bridge.SLF4JBridgeHandler

@OpenAPIDefinition(
    info = Info(
        title = "core-api",
        version = "0.1",
        description = "Main service of the Finances Box application",
        license = License(name = "MIT", url = "https://github.com/FinancesBox/core-api/blob/main/LICENSE"),
        contact = Contact(url = "https://github.com/romanovich23", name = "Javier", email = "javier.rmgz@gmail.com")
    )
)
object API {
    const val API_V1_URI = "/api/v1"
}

fun main(args: Array<String>) {
    // Bridge JUL to Slf4j
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
    run(*args)
}

