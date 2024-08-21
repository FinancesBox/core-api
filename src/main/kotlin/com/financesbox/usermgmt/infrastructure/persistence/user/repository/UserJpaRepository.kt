package com.financesbox.usermgmt.infrastructure.persistence.user.repository

import com.financesbox.shared.domain.repository.AbstractDomainRepository
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import com.financesbox.usermgmt.infrastructure.persistence.user.entity.UserEntity
import com.financesbox.usermgmt.infrastructure.persistence.user.mapper.UserMapper
import jakarta.inject.Singleton
import java.util.*

@Singleton
class UserJpaRepository(private val mapper: UserMapper, private val repository: UserEntityJpaRepository) :
    AbstractDomainRepository<UserEntity, User, UUID>(mapper, repository), UserRepository {

    override fun findByName(name: String): Optional<User> {
        val optEntity = repository.findOneByName(name)
        return checkAndReturn(optEntity)
    }

    override fun findByEmail(email: String): Optional<User> {
        val optEntity = repository.findOneByEmail(email)
        return checkAndReturn(optEntity)
    }

}