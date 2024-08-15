package com.financesbox.usermgmt.infrastructure.web.user.createuser.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

@Serdeable
@Introspected
@Schema(description = "User creation request model")
data class CreateUserRequestModel(
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
)