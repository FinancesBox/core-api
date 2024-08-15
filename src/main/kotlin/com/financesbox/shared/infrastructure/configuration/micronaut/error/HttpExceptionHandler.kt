package com.financesbox.shared.infrastructure.configuration.micronaut.error

import com.financesbox.shared.domain.exception.DomainException
import com.financesbox.shared.domain.exception.InvalidInputDomainException
import com.financesbox.shared.domain.exception.UnsupportedOperationDomainException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Singleton
class HttpExceptionHandler : ExceptionHandler<DomainException, HttpResponse<HttpExceptionMessage>> {

    override fun handle(
        request: HttpRequest<*>?, exception: DomainException?
    ): HttpResponse<HttpExceptionMessage> {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        if (exception != null) {
            status = when (exception::class) {
                InvalidInputDomainException::class -> HttpStatus.BAD_REQUEST
                UnsupportedOperationDomainException::class -> HttpStatus.CONFLICT
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
        }
        return HttpResponse.status<HttpStatus>(status).body(
            HttpExceptionMessage(
                message = exception?.message ?: "Unexpected error",
                status
            )
        )
    }


}