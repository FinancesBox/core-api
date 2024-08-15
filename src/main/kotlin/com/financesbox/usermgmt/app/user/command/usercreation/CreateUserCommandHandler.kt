package com.financesbox.usermgmt.app.user.command.usercreation

import com.financesbox.shared.application.cqs.command.CommandHandler
import com.financesbox.shared.application.event.EventBus
import com.financesbox.usermgmt.app.security.encryption.service.PasswordEncryptionService
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import com.financesbox.usermgmt.domain.user.service.UserCreationDomainService
import jakarta.inject.Singleton

@Singleton
class CreateUserCommandHandler(
    private val domainService: UserCreationDomainService,
    private val eventBus: EventBus,
    private val encryptionService: PasswordEncryptionService
) : CommandHandler<CreateUserCommand, UserCreatedEvent> {

    override suspend fun handle(command: CreateUserCommand): UserCreatedEvent {
        val encryptedPassword = encryptionService.hashPassword(command.password)
        val user = domainService.create(
            command.name, command.email, encryptedPassword, command.roles
        )
        val event =
            UserCreatedEvent(user.id, user.name, user.email, user.password, user.roles.map { role -> role.name })
        eventBus.publish(event)
        return event
    }

}