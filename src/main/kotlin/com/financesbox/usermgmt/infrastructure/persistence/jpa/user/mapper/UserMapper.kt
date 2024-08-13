package com.financesbox.usermgmt.infrastructure.persistence.jpa.user.mapper

import com.financesbox.shared.infrastructure.persistence.mapper.Mapper
import com.financesbox.usermgmt.domain.user.model.User
import com.financesbox.usermgmt.infrastructure.persistence.jpa.user.entity.UserEntity
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserMapper(@Inject private val userRoleMapper: UserRoleMapper) : Mapper<UserEntity, User> {

    override fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            password = entity.password,
            roles = entity.roles.map { role -> userRoleMapper.toDomain(role) },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun toEntity(domainModel: User): UserEntity {
        return UserEntity(
            id = domainModel.id,
            name = domainModel.name,
            email = domainModel.email,
            password = domainModel.password,
            roles = domainModel.roles.map { role -> userRoleMapper.toEntity(role) },
            createdAt = domainModel.createdAt,
            updatedAt = domainModel.updatedAt
        )
    }

}