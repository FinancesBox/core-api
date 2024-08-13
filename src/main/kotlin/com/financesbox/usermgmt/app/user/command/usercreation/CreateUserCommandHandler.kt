package com.financesbox.usermgmt.app.user.command.usercreation

import com.financesbox.shared.application.cqs.command.CommandHandler
import com.financesbox.shared.application.event.EventBus
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import com.financesbox.usermgmt.domain.user.service.UserCreationDomainService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class CreateUserCommandHandler(
    @Inject private val domainService: UserCreationDomainService, @Inject private val eventBus: EventBus
) : CommandHandler<CreateUserCommand, UserCreatedEvent> {

    override suspend fun handle(command: CreateUserCommand): UserCreatedEvent {
        val user = domainService.create(
            command.name, command.email, command.password, command.roles
        )
        val event = UserCreatedEvent(user.id, user.name, user.email, user.password, user.roles)
        eventBus.publish(event)
        return event
    }

}