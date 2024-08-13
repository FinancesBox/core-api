package com.financesbox.usermgmt.infrastructure.web.user.createuser.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

@Serdeable
@Introspected
data class CreateUserRequestModel(
    @NotBlank val name: String,
    @NotBlank val email: String,
    @NotBlank val password: String,
    @NotEmpty val roles: List<String>
)