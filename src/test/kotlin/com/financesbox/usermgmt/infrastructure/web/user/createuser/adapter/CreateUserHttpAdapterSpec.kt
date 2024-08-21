package com.financesbox.usermgmt.infrastructure.web.user.createuser.adapter

import com.financesbox.shared.application.cqs.command.CommandBus
import com.financesbox.shared.domain.exception.UnsupportedOperationDomainException
import com.financesbox.shared.infrastructure.configuration.micronaut.api.API
import com.financesbox.usermgmt.app.user.command.createuser.CreateUserCommand
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserRequestModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserResponseModel
import io.github.serpro69.kfaker.Faker
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpRequest
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
import java.util.*

@MicronautTest
class CreateUserHttpAdapterSpec(
    @Client(API.API_V1_URI) private val httpClient: HttpClient,
    private val commandBus: CommandBus,
    private val faker: Faker,
) : FunSpec({
    val requestModel = CreateUserRequestModel(
        name = "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}${faker.random.nextInt()}",
        email = faker.internet.email(),
        password = faker.random.randomString(),
        roles = listOf("ADMIN"),
    )

    test("createUser should return 201 status and create an user when the command is executed") {
        val commandBusMocked = getMock(commandBus)

        val event = UserCreatedEvent(
            id = UUID.randomUUID(),
            name = requestModel.name,
            email = requestModel.email,
            password = requestModel.password,
            roles = requestModel.roles
        )

        every { commandBusMocked.syncExecute(any(CreateUserCommand::class)) } returns event

        val response = httpClient.toBlocking()
            .exchange(HttpRequest.POST("users", requestModel), CreateUserResponseModel::class.java)

        response.status shouldBe HttpStatus.CREATED
        response.body.isPresent shouldBe true
        response.body().userId shouldNotBe null

        verify(exactly = 1) {
            commandBusMocked.syncExecute(any(CreateUserCommand::class))
        }
    }

    test("createUser should return 409 status when the user already exists") {
        val commandBusMocked = getMock(commandBus)
        every { commandBusMocked.syncExecute(any(CreateUserCommand::class)) } throws UnsupportedOperationDomainException(
            "User already exists"
        )

        val exception = shouldThrow<HttpClientResponseException> {
            httpClient.toBlocking()
                .exchange(HttpRequest.POST("users", requestModel), CreateUserResponseModel::class.java)
        }

        exception.status shouldBe HttpStatus.CONFLICT

        verify(exactly = 1) {
            commandBusMocked.syncExecute(any(CreateUserCommand::class))
        }
    }

}) {

    @MockBean(CommandBus::class)
    fun commandBus(): CommandBus {
        return mockk()
    }

}
