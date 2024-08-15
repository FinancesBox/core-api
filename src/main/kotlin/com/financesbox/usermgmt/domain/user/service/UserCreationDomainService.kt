package com.financesbox.usermgmt.domain.user.service

import com.financesbox.usermgmt.domain.user.exception.UserAlreadyExistsDomainExceptionUnsupported
import com.financesbox.usermgmt.domain.user.exception.UserRolesMismatchDomainException
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import com.financesbox.usermgmt.domain.user.repository.UserRoleRepository
import jakarta.inject.Singleton
import java.time.Instant
import java.util.*

@Singleton
class UserCreationDomainService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository
) {

    /**
     * Validates and creates a user
     */
    fun create(
        name: String, email: String, password: String, roles: List<String>
    ): User {
        require(userRepository.findByName(name).isEmpty && userRepository.findByEmail(email).isEmpty) {
            throw UserAlreadyExistsDomainExceptionUnsupported()
        }
        val rolesFound = userRoleRepository.findByNameList(roles)
        require(rolesFound.size == roles.size) {
            throw UserRolesMismatchDomainException("Expected ${rolesFound.size} roles, but found ${rolesFound.size}.")
        }
        val user = User(
            id = UUID.randomUUID(),
            name = name,
            email = email,
            password = password,
            roles = rolesFound,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        return userRepository.save(user)
    }

}