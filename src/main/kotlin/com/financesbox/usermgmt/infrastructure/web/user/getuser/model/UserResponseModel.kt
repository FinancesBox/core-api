package com.financesbox.usermgmt.infrastructure.web.user.getuser.model

import io.micronaut.serde.annotation.Serdeable
import java.time.Instant
import java.util.*

@Serdeable
data class UserResponseModel(
    val id: UUID,
    val name: String,
    val email: String,
    val roles: List<String>,
    val createdAt: Instant,
)