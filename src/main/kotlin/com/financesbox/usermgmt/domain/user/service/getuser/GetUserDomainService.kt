package com.financesbox.usermgmt.domain.user.service.getuser

import com.financesbox.shared.domain.exception.InvalidInputDomainException
import com.financesbox.shared.domain.service.DomainService
import com.financesbox.usermgmt.domain.user.exception.UserNotFoundDomainException
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import jakarta.inject.Singleton
import java.util.*

@Singleton
class GetUserDomainService(private val repository: UserRepository) : DomainService<GetUserDTO, User> {

    override fun execute(dto: GetUserDTO): User {
        return when (true) {
            (dto.id != null) -> findById(dto.id)
            else -> throw InvalidInputDomainException("Any parameter has been specified to find the user")
        }
    }

    private fun findById(id: UUID): User {
        val userOpt = repository.findById(id)
        if (userOpt.isEmpty) {
            throw UserNotFoundDomainException("User not found by id $id")
        }
        return userOpt.get()
    }

}