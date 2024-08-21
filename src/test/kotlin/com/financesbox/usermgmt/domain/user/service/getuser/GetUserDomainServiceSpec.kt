package com.financesbox.usermgmt.domain.user.service.getuser

import com.financesbox.shared.domain.exception.InvalidInputDomainException
import com.financesbox.usermgmt.domain.user.exception.UserNotFoundDomainException
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import io.github.serpro69.kfaker.Faker
import io.kotest.assertions.throwables.shouldThrow
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
class GetUserDomainServiceSpec(
    private val domainService: GetUserDomainService,
    private val userRepository: UserRepository,
    private val faker: Faker,
) : FunSpec({

    test("execute should retrieve an user when a id is given") {
        val repositoryMocked = getMock(userRepository)
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

        every { repositoryMocked.findById(userExpected.id) } returns Optional.of(userExpected)

        val user = domainService.execute(GetUserDTO(userExpected.id))

        user shouldBe userExpected
    }

    test("execute should throw an exception when a user is not found by id") {
        val repositoryMocked = getMock(userRepository)
        val id = UUID.randomUUID()

        every { repositoryMocked.findById(id) } returns Optional.empty()

        val exception = shouldThrow<UserNotFoundDomainException> {
            domainService.execute(GetUserDTO(id))
        }

        exception.shouldNotBeNull()
        exception.message.shouldNotBeNull()

        verify(exactly = 1) { repositoryMocked.findById(id) }
    }

    test("execute should throw an exception when any parameter is specified in the DTO") {
        val exception = shouldThrow<InvalidInputDomainException> {
            domainService.execute(GetUserDTO(id = null))
        }

        exception.shouldNotBeNull()
        exception.message.shouldNotBeNull()
    }

}) {

    @MockBean(UserRepository::class)
    fun userRepository(): UserRepository {
        return mockk()
    }

}
