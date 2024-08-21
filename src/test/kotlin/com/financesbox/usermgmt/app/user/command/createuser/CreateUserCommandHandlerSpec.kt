package com.financesbox.usermgmt.app.user.command.createuser

import com.financesbox.shared.application.cqs.command.CommandBus
import com.financesbox.shared.application.event.EventBus
import com.financesbox.usermgmt.app.security.encryption.service.PasswordEncryptionService
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.domain.user.service.createuser.CreateUserDomainService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import java.time.Instant
import java.util.*

@MicronautTest
class CreateUserCommandHandlerSpec(
    private val eventBus: EventBus,
    private val commandBus: CommandBus,
    private val domainService: CreateUserDomainService,
    private val encryptionService: PasswordEncryptionService,
) : FunSpec({

    test("should create user and publish event") {
        val eventBusMocked = getMock(eventBus)
        val domainServiceMocked = getMock(domainService)
        val encryptionServiceMocked = getMock(encryptionService)

        val command = CreateUserCommand(
            name = "John Doe", email = "john.doe@example.com", password = "securePassword", roles = listOf("ADMIN")
        )

        val encryptedPassword = "encryptedPassword"
        val user = User(
            id = UUID.randomUUID(),
            name = command.name,
            email = command.email,
            password = encryptedPassword,
            roles = command.roles.mapIndexed { index, role ->
                UserRole(
                    id = index, name = role, createdAt = Instant.now(), updatedAt = Instant.now()
                )
            },
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        coEvery { encryptionServiceMocked.hashPassword(command.password) } returns encryptedPassword
        coEvery {
            domainServiceMocked.execute(any())
        } returns user
        val slot = slot<UserCreatedEvent>()
        coEvery { eventBusMocked.publish(capture(slot)) } returns Unit


        val result = commandBus.syncExecute(command)

        coVerify(exactly = 1) { encryptionServiceMocked.hashPassword(command.password) }
        coVerify(exactly = 1) {
            domainServiceMocked.execute(any())
        }
        coVerify(exactly = 1) { eventBusMocked.publish(any()) }

        result.id shouldBe user.id
        result.name shouldBe user.name
        result.email shouldBe user.email
        result.password shouldBe user.password
        result.roles shouldBe user.roles.map { it.name }

        slot.captured.id shouldBe user.id
        slot.captured.name shouldBe user.name
        slot.captured.email shouldBe user.email
        slot.captured.password shouldBe user.password
        slot.captured.roles shouldBe user.roles.map { it.name }
    }
}) {

    @MockBean(CreateUserDomainService::class)
    fun domainService(): CreateUserDomainService {
        return mockk()
    }

    @MockBean(EventBus::class)
    fun eventBus(): EventBus {
        return mockk(relaxed = true)
    }

    @MockBean(PasswordEncryptionService::class)
    fun passwordEncryptionService(): PasswordEncryptionService {
        return mockk()
    }

}

