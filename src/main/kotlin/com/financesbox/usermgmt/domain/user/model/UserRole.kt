package com.financesbox.usermgmt.domain.user.model

import com.financesbox.shared.domain.model.DomainModel
import java.time.Instant

data class UserRole(val id: Int, val name: String, override val createdAt: Instant, override val updatedAt: Instant) :
    DomainModel(createdAt, updatedAt)