package com.financesbox.usermgmt.infrastructure.persistence.user.repository

import com.financesbox.shared.domain.repository.AbstractDomainRepository
import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.domain.user.repository.UserRoleRepository
import com.financesbox.usermgmt.infrastructure.persistence.user.entity.UserRoleEntity
import com.financesbox.usermgmt.infrastructure.persistence.user.mapper.UserRoleMapper
import jakarta.inject.Singleton

@Singleton
class UserRoleJpaRepository(private val repository: UserRoleEntityJpaRepository, private val mapper: UserRoleMapper) :
    AbstractDomainRepository<UserRoleEntity, UserRole, Int>(mapper, repository), UserRoleRepository {

    override fun findByNameList(names: List<String>): List<UserRole> {
        return repository.findByNameInList(names).map { entity -> mapper.toDomain(entity) }
    }

}