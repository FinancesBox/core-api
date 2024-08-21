package com.financesbox.usermgmt.domain.user.model

import com.financesbox.shared.domain.model.DomainModel
import java.time.Instant
import java.util.*

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val password: String,
    val roles: List<UserRole>,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : DomainModel(createdAt, updatedAt)