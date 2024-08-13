package com.financesbox.usermgmt.domain.user.model

import com.financesbox.shared.domain.model.DomainModel
import com.financesbox.usermgmt.domain.user.exception.UserRoleIdInvalidDomainException
import com.financesbox.usermgmt.domain.user.exception.UserRoleNameInvalidDomainException
import java.time.Instant

data class UserRole(val id: Int, val name: String, override val createdAt: Instant, override val updatedAt: Instant) :
    DomainModel(createdAt, updatedAt) {
    init {
        require(id > -1) {
            throw UserRoleIdInvalidDomainException("User role ID cannot be less than zero")
        }
        require(name.isNotBlank()) {
            throw UserRoleNameInvalidDomainException("User role name cannot be blank")
        }
    }
}