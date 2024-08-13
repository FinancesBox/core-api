package com.financesbox.usermgmt.infrastructure.persistence.jpa.user.repository

import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.domain.user.repository.UserRepository
import com.financesbox.usermgmt.infrastructure.persistence.jpa.user.mapper.UserMapper
import jakarta.inject.Singleton
import java.util.*

@Singleton
class UserJpaRepository(private val mapper: UserMapper, private val repository: UserEntityJpaRepository) :
    UserRepository {

    override fun findByName(name: String): Optional<User> {
        val optEntity = repository.findOneByName(name)
        if (optEntity.isPresent) {
            return Optional.of(mapper.toDomain(optEntity.get()))
        }
        return Optional.empty()
    }

    override fun findByEmail(email: String): Optional<User> {
        val optEntity = repository.findOneByEmail(email)
        if (optEntity.isPresent) {
            return Optional.of(mapper.toDomain(optEntity.get()))
        }
        return Optional.empty()
    }

    override fun save(model: User): User {
        return mapper.toDomain(repository.save(mapper.toEntity(model)))
    }

    override fun update(model: User): User {
        return mapper.toDomain(repository.update(mapper.toEntity(model)))
    }

}