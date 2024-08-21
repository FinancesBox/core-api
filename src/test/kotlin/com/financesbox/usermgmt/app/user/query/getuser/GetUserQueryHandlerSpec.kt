package com.financesbox.usermgmt.app.user.query.getuser

import com.financesbox.shared.application.cqs.query.QueryBus
import com.financesbox.usermgmt.app.user.query.getuser.model.UserQueryModel
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.domain.user.service.getuser.GetUserDTO
import com.financesbox.usermgmt.domain.user.service.getuser.GetUserDomainService
import io.github.serpro69.kfaker.Faker
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.Instant
import java.util.*

@MicronautTest
class GetUserQueryHandlerSpec(private val queryBus: QueryBus, private val domainService: GetUserDomainService) :
    FunSpec({

        val faker = Faker()

        test("handle should retrieve an user") {
            val domainServiceMocked = getMock(domainService)
            val userExpected = User(
                id = UUID.randomUUID(),
                name = "${faker.name.firstName().lowercase()}.${
                    faker.name.lastName().lowercase()
                }${faker.random.nextInt()}",
                password = faker.random.randomString(),
                email = faker.internet.email(),
                roles = listOf(UserRole(id = 1, name = "ADMIN", createdAt = Instant.now(), updatedAt = Instant.now())),
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )

            every { domainServiceMocked.execute(any(GetUserDTO::class)) } returns userExpected

            val queryModel: UserQueryModel = queryBus.syncExecute(GetUserQuery(id = userExpected.id))

            queryModel.shouldNotBeNull()
            queryModel.id shouldBe userExpected.id
            queryModel.name shouldBe userExpected.name
            queryModel.email shouldBe userExpected.email
            queryModel.roles shouldBe userExpected.roles.map { role -> role.name }
            queryModel.createdAt shouldBe userExpected.createdAt

            verify(exactly = 1) { domainServiceMocked.execute(any(GetUserDTO::class)) }
        }

    }) {

    @MockBean(GetUserDomainService::class)
    fun domainService(): GetUserDomainService {
        return mockk()
    }

}
