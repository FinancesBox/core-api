package com.financesbox

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.slf4j.bridge.SLF4JBridgeHandler

@OpenAPIDefinition(
    info = Info(
        title = "core-api",
        version = "0.1"
    )
)
object Api

fun main(args: Array<String>) {
    // Bridge JUL to Slf4j
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    run(*args)
}

