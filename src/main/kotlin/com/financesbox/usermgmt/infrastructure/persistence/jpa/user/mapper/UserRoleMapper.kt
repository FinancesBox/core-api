package com.financesbox.usermgmt.infrastructure.persistence.jpa.user.mapper

import com.financesbox.shared.infrastructure.persistence.mapper.Mapper
import com.financesbox.usermgmt.domain.user.model.UserRole
import com.financesbox.usermgmt.infrastructure.persistence.jpa.user.entity.UserRoleEntity
import jakarta.inject.Singleton

@Singleton
class UserRoleMapper : Mapper<UserRoleEntity, UserRole> {

    override fun toDomain(entity: UserRoleEntity): UserRole {
        return UserRole(
            id = entity.id,
            name = entity.name,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun toEntity(domainModel: UserRole): UserRoleEntity {
        return UserRoleEntity(
            id = domainModel.id,
            name = domainModel.name,
            createdAt = domainModel.createdAt,
            updatedAt = domainModel.updatedAt
        )
    }

}