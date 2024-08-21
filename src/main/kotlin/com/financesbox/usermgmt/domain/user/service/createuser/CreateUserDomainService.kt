package com.financesbox.usermgmt.domain.user.service.createuser

import com.financesbox.shared.domain.service.DomainService
import com.financesbox.usermgmt.domain.user.exception.UserAlreadyExistsDomainExceptionUnsupported
import com.financesbox.usermgmt.domain.user.exception.UserRolesMismatchDomainException
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import com.financesbox.usermgmt.domain.user.repository.UserRoleRepository
import jakarta.inject.Singleton
import jakarta.validation.Valid
import java.time.Instant
import java.util.*

@Singleton
class CreateUserDomainService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
) : DomainService<CreateUserDTO, User> {

    /**
     * Validates and creates a user
     */
    override fun execute(@Valid dto: CreateUserDTO): User {
        require(userRepository.findByName(dto.name).isEmpty && userRepository.findByEmail(dto.email).isEmpty) {
            throw UserAlreadyExistsDomainExceptionUnsupported()
        }
        val rolesFound = userRoleRepository.findByNameList(dto.roles)
        require(rolesFound.size == dto.roles.size) {
            throw UserRolesMismatchDomainException("Expected ${rolesFound.size} roles, but found ${rolesFound.size}.")
        }
        val user = User(
            id = UUID.randomUUID(),
            name = dto.name,
            email = dto.email,
            password = dto.password,
            roles = rolesFound,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        return userRepository.save(user)
    }

}