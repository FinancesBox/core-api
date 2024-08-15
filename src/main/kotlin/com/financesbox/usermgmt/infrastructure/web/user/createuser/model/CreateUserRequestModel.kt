package com.financesbox.usermgmt.infrastructure.web.user.createuser.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

@Serdeable
@Introspected
@Schema(description = "User creation request model")
data class CreateUserRequestModel(
    @NotBlank val name: String,
    @NotBlank val email: String,
    @NotBlank val password: String,
    @NotEmpty val roles: List<String>
)