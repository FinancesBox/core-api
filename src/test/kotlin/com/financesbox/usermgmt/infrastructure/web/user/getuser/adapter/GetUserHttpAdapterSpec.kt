package com.financesbox.usermgmt.infrastructure.web.user.getuser.adapter

import com.financesbox.shared.application.cqs.query.QueryBus
import com.financesbox.shared.domain.exception.DomainException
import com.financesbox.shared.domain.exception.EntityNotFoundDomainException
import com.financesbox.shared.infrastructure.configuration.micronaut.api.API
import com.financesbox.usermgmt.app.user.query.getuser.GetUserQuery
import com.financesbox.usermgmt.app.user.query.getuser.model.UserQueryModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserResponseModel
import com.financesbox.usermgmt.infrastructure.web.user.getuser.model.UserResponseModel
import io.github.serpro69.kfaker.Faker
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.Instant
import java.util.*

@MicronautTest
class GetUserHttpAdapterSpec(
    @Client(API.API_V1_URI) private val httpClient: HttpClient, private val queryBus: QueryBus,
) : FunSpec({

    val faker = Faker()

    test("getUser should return 200 status and retrieve an user when the query is executed") {
        val queryBusMocked = getMock(queryBus)

        val responseModel = UserResponseModel(
            id = UUID.randomUUID(),
            name = "${faker.name.firstName().lowercase()}.${
                faker.name.lastName().lowercase()
            }${faker.random.nextInt()}",
            email = faker.internet.email(),
            roles = listOf("ADMIN"),
            createdAt = Instant.now()
        )

        val queryModel = UserQueryModel(
            id = responseModel.id,
            name = responseModel.name,
            email = responseModel.email,
            roles = responseModel.roles,
            createdAt = responseModel.createdAt
        )

        every { queryBusMocked.syncExecute(any(GetUserQuery::class)) } returns queryModel

        val response = httpClient.toBlocking().exchange("users/${responseModel.id}", UserResponseModel::class.java)

        response.status shouldBe HttpStatus.OK
        response.body.isPresent shouldBe true
        response.body() shouldBe responseModel

        verify(exactly = 1) {
            queryBusMocked.syncExecute(any(GetUserQuery::class))
        }
    }

    test("getUser should return 404 status when the user does not exist") {
        val queryBusMocked = getMock(queryBus)
        every { queryBusMocked.syncExecute(any(GetUserQuery::class)) } throws EntityNotFoundDomainException(
            "User does not exists"
        )

        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking().exchange("users/${UUID.randomUUID()}", CreateUserResponseModel::class.java)
        }

        exception.status shouldBe HttpStatus.NOT_FOUND

        verify(exactly = 1) {
            queryBusMocked.syncExecute(any(GetUserQuery::class))
        }
    }

    test("getUser should return 500 when the exception is unknown") {
        val queryBusMocked = getMock(queryBus)
        every { queryBusMocked.syncExecute(any(GetUserQuery::class)) } throws DomainException(
            "Fake error"
        )

        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking().exchange("users/${UUID.randomUUID()}", CreateUserResponseModel::class.java)
        }

        exception.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR

        verify(exactly = 1) {
            queryBusMocked.syncExecute(any(GetUserQuery::class))
        }
    }

}) {

    @MockBean(QueryBus::class)
    fun queryBus(): QueryBus {
        return mockk()
    }

}
