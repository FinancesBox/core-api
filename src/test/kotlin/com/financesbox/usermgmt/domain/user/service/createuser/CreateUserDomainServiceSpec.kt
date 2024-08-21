package com.financesbox.usermgmt.domain.user.service.createuser

import com.financesbox.usermgmt.domain.user.exception.UserAlreadyExistsDomainExceptionUnsupported
import com.financesbox.usermgmt.domain.user.exception.UserRolesMismatchDomainException
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import com.financesbox.usermgmt.domain.user.repository.UserRoleRepository
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
import java.time.Instant
import java.util.*

@MicronautTest
class CreateUserDomainServiceSpec(
    private val domainService: CreateUserDomainService,
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val faker: Faker,
) : FunSpec({

    val user = User(
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

    test("execute should retrieve an user when the data is correct") {
        val repositoryMocked = getMock(userRepository)
        val userRoleRepositoryMocked = getMock(userRoleRepository)

        every { repositoryMocked.findByEmail(user.email) } returns Optional.empty()
        every { repositoryMocked.findByName(user.name) } returns Optional.empty()
        every { userRoleRepositoryMocked.findByNameList(any()) } returns user.roles
        every { repositoryMocked.save(any(User::class)) } returns user

        val userReceived = domainService.execute(
            CreateUserDTO(
                name = user.name,
                email = user.email,
                password = user.password,
                roles = user.roles.map { it.name }
            )
        )

        userReceived shouldBe user
    }

    test("execute should throw an exception when the email already exists") {
        val repositoryMocked = getMock(userRepository)
        val userRoleRepositoryMocked = getMock(userRoleRepository)

        every { repositoryMocked.findByEmail(user.email) } returns Optional.of(user)
        every { repositoryMocked.findByName(user.name) } returns Optional.empty()
        every { userRoleRepositoryMocked.findByNameList(any()) } returns user.roles
        every { repositoryMocked.save(any(User::class)) } returns user

        val exception = shouldThrow<UserAlreadyExistsDomainExceptionUnsupported> {
            domainService.execute(
                CreateUserDTO(
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    roles = user.roles.map { it.name }
                )
            )
        }

        exception.shouldNotBeNull()
    }

    test("execute should throw an exception when the name already exists") {
        val repositoryMocked = getMock(userRepository)
        val userRoleRepositoryMocked = getMock(userRoleRepository)

        every { repositoryMocked.findByEmail(user.email) } returns Optional.empty()
        every { repositoryMocked.findByName(user.name) } returns Optional.of(user)
        every { userRoleRepositoryMocked.findByNameList(any()) } returns user.roles
        every { repositoryMocked.save(any(User::class)) } returns user

        val exception = shouldThrow<UserAlreadyExistsDomainExceptionUnsupported> {
            domainService.execute(
                CreateUserDTO(
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    roles = user.roles.map { it.name }
                )
            )
        }

        exception.shouldNotBeNull()
    }

    test("execute should throw an exception when the role does not exist") {
        val repositoryMocked = getMock(userRepository)
        val userRoleRepositoryMocked = getMock(userRoleRepository)

        every { repositoryMocked.findByEmail(user.email) } returns Optional.empty()
        every { repositoryMocked.findByName(user.name) } returns Optional.empty()
        every { userRoleRepositoryMocked.findByNameList(any()) } returns emptyList()
        every { repositoryMocked.save(any(User::class)) } returns user

        val exception = shouldThrow<UserRolesMismatchDomainException> {
            domainService.execute(
                CreateUserDTO(
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    roles = listOf(faker.random.randomString())
                )
            )
        }

        exception.shouldNotBeNull()
    }


}) {

    @MockBean(UserRepository::class)
    fun userRepository(): UserRepository {
        return mockk()
    }

    @MockBean(UserRoleRepository::class)
    fun userRoleRepository(): UserRoleRepository {
        return mockk()
    }

}

