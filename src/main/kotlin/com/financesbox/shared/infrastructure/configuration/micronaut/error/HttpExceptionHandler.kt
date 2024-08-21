package com.financesbox.shared.infrastructure.configuration.micronaut.error

import com.financesbox.shared.domain.exception.DomainException
import com.financesbox.shared.domain.exception.EntityNotFoundDomainException
import com.financesbox.shared.domain.exception.InvalidInputDomainException
import com.financesbox.shared.domain.exception.UnsupportedOperationDomainException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import kotlin.reflect.KClass
import kotlin.reflect.full.createType

@Singleton
class HttpExceptionHandler : ExceptionHandler<DomainException, HttpResponse<HttpExceptionMessage>> {

    override fun handle(
        request: HttpRequest<*>?, exception: DomainException?,
    ): HttpResponse<HttpExceptionMessage> {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        if (exception != null) {
            status = when (true) {
                (isClassOrSubclassOf(InvalidInputDomainException::class, exception::class)) -> HttpStatus.BAD_REQUEST
                (isClassOrSubclassOf(
                    UnsupportedOperationDomainException::class, exception::class
                )),
                    -> HttpStatus.CONFLICT

                (isClassOrSubclassOf(EntityNotFoundDomainException::class, exception::class)) -> HttpStatus.NOT_FOUND
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
        }
        return HttpResponse.status<HttpStatus>(status).body(
            HttpExceptionMessage(
                message = exception?.message ?: "Unexpected error", status
            )
        )
    }

    private fun isClassOrSubclassOf(kClass: KClass<out Throwable>, to: KClass<out Throwable>): Boolean {
        return kClass == to || isSubclassOf(kClass, to)
    }

    private fun isSubclassOf(superClass: KClass<out Throwable>, subClass: KClass<out Throwable>): Boolean {
        return subClass.supertypes.contains(superClass.createType())
    }

}