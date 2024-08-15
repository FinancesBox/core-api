package com.financesbox.shared.infrastructure.configuration.micronaut.error

import io.micronaut.http.HttpStatus
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class HttpExceptionMessage(val message: String, val status: HttpStatus)