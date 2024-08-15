package com.financesbox.usermgmt.app.user.command.createuser

import com.financesbox.shared.application.cqs.command.CommandHandler
import com.financesbox.shared.application.event.EventBus
import com.financesbox.usermgmt.app.security.encryption.service.PasswordEncryptionService
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import com.financesbox.usermgmt.domain.user.service.CreateUserDTO
import com.financesbox.usermgmt.domain.user.service.CreateUserDomainService
import jakarta.inject.Singleton
import jakarta.validation.Valid

@Singleton
class CreateUserCommandHandler(
    private val domainService: CreateUserDomainService,
    private val eventBus: EventBus,
    private val encryptionService: PasswordEncryptionService
) : CommandHandler<CreateUserCommand, UserCreatedEvent> {

    override suspend fun handle(@Valid command: CreateUserCommand): UserCreatedEvent {
        val encryptedPassword = encryptionService.hashPassword(command.password)
        val user = domainService.execute(CreateUserDTO(command.name, command.email, encryptedPassword, command.roles))
        val event =
            UserCreatedEvent(user.id, user.name, user.email, user.password, user.roles.map { role -> role.name })
        eventBus.publish(event)
        return event
    }

}