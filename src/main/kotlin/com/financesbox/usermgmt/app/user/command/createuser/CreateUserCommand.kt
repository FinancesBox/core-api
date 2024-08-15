package com.financesbox.usermgmt.app.user.command.createuser

import com.financesbox.shared.application.cqs.command.Command
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent
import io.micronaut.core.annotation.Introspected
import jakarta.validation.constraints.*

@Introspected
class CreateUserCommand(
    @NotBlank @Size(min = 3, max = 20) @Pattern(
        regexp = "^[a-zA-Z0-9_]*\$", message = "Username must contain only alphanumeric characters and underscores"
    ) val name: String,
    @NotBlank @Email val email: String,
    @NotBlank @Size(
        min = 8,
        max = 20,
        message = "Password must be between 8 and 20 characters long"
    ) val password: String,
    @NotEmpty val roles: List<String>
) : Command<UserCreatedEvent>()