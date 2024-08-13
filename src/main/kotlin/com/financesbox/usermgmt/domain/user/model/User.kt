package com.financesbox.usermgmt.domain.user.model

import com.financesbox.shared.domain.model.DomainModel
import com.financesbox.usermgmt.domain.user.exception.UserEmailInvalidDomainException
import com.financesbox.usermgmt.domain.user.exception.UserPasswordInvalidDomainException
import com.financesbox.usermgmt.domain.user.exception.UsernameInvalidDomainException
import java.time.Instant
import java.util.*

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val password: String,
    val roles: List<UserRole>,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : DomainModel(createdAt, updatedAt) {
    init {
        require(name.isNotBlank()) {
            throw UsernameInvalidDomainException("Username cannot be blank")
        }
        require(email.isNotBlank()) {
            throw UserEmailInvalidDomainException("User email cannot be blank")
        }
        require(password.isNotBlank()) {
            throw UserPasswordInvalidDomainException("User password cannot be blank")
        }
    }
}