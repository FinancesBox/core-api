package com.financesbox.usermgmt.infrastructure.web.user.createuser.adapter

import com.financesbox.shared.infrastructure.configuration.micronaut.api.API
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserRequestModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserResponseModel
import io.github.serpro69.kfaker.Faker
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import kotlin.random.Random

@MicronautTest
class CreateUserHttpAdapterIntegrationSpec(@Client(API.API_V1_URI) private val httpClient: HttpClient) : FeatureSpec({

    feature("user creation") {
        val faker = Faker()

        fun generatePassword(length: Int = 12): String {
            val upperCaseLetters = ('A'..'Z').toList()
            val lowerCaseLetters = ('a'..'z').toList()
            val numbers = ('0'..'9').toList()
            val specialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=')

            val allChars = upperCaseLetters + lowerCaseLetters + numbers + specialChars
            return (1..length).map { allChars.random(Random(faker.random.nextInt())) }.joinToString("")
        }

        fun generateUsername(): String {
            return "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}${faker.random.nextInt()}"
        }

        scenario("should return 201 status when the request is ok") {
            val requestModel = CreateUserRequestModel(
                name = generateUsername(),
                email = faker.internet.email(),
                password = generatePassword(),
                roles = listOf("ADMIN"),
            )

            val response = httpClient.toBlocking()
                .exchange(HttpRequest.POST("users", requestModel), CreateUserResponseModel::class.java)

            response.status shouldBe HttpStatus.CREATED
            val responseBody = requireNotNull(response.body()) { "Response body should not be null" }
            responseBody.userId shouldNotBe null
        }
    }

})

