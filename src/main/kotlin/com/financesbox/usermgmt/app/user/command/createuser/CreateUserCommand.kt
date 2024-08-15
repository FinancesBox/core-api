package com.financesbox.usermgmt.app.user.command.createuser

import com.financesbox.shared.application.cqs.command.Command
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import jakarta.validation.constraints.NotBlank

class CreateUserCommand(
    @NotBlank val name: String,
    @NotBlank val email: String,
    @NotBlank val password: String,
    @NotBlank val roles: List<String>
) : Command<UserCreatedEvent>()