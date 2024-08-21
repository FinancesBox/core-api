package com.financesbox.usermgmt.infrastructure.web.user.getuser.adapter

import com.financesbox.shared.infrastructure.configuration.micronaut.api.API
import com.financesbox.usermgmt.infrastructure.web.user.getuser.model.UserResponseModel
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.Sql
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import java.util.*

@MicronautTest(transactionMode = TransactionMode.SINGLE_TRANSACTION)
@Sql(value = ["classpath:sql/usermgmt/getuser/inserts.sql"], phase = Sql.Phase.BEFORE_ALL)
class GetUserHttpAdapterIntegrationSpec(@Client(API.API_V1_URI) private val httpClient: HttpClient) : FeatureSpec({

    feature("get user") {

        scenario("should return an user successfully when the user exists") {
            val userId = UUID.fromString("63ce9f6e-3cd1-41a9-af61-e1c232692aea")

            val response = httpClient.toBlocking().exchange("users/${userId}", UserResponseModel::class.java)

            response.status shouldBe HttpStatus.OK
            response.body.isPresent shouldBe true
            val user = response.body.get()
            user.id shouldBe userId
            user.name.shouldNotBeNull()
            user.email.shouldNotBeNull()
            user.roles.shouldNotBeNull()
            user.roles.shouldNotBeEmpty()
            user.createdAt.shouldNotBeNull()
        }

        scenario("should return an status 404 when the user does not exist") {
            val userId = UUID.randomUUID()

            val response = shouldThrow<HttpClientResponseException> {
                httpClient.toBlocking().exchange("users/${userId}", UserResponseModel::class.java)
            }

            response.status shouldBe HttpStatus.NOT_FOUND
        }

    }

})
