package com.financesbox.usermgmt.infrastructure.persistence.jpa.user.repository

import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.domain.user.repository.UserRoleRepository
import com.financesbox.usermgmt.infrastructure.persistence.jpa.user.mapper.UserRoleMapper
import jakarta.inject.Singleton

@Singleton
class UserRoleJpaRepository(private val repository: UserRoleEntityJpaRepository, private val mapper: UserRoleMapper) :
    UserRoleRepository {

    override fun findByNameList(names: List<String>): List<UserRole> {
        return repository.findByNameInList(names).map { entity -> mapper.toDomain(entity) }
    }

    override fun save(model: UserRole): UserRole {
        return mapper.toDomain(repository.save(mapper.toEntity(model)))
    }

    override fun update(model: UserRole): UserRole {
        return mapper.toDomain(repository.update(mapper.toEntity(model)))
    }

}