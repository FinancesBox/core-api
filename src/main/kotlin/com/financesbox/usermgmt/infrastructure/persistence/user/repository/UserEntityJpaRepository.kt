package com.financesbox.usermgmt.infrastructure.persistence.user.repository

import com.financesbox.usermgmt.infrastructure.persistence.user.entity.UserEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface UserEntityJpaRepository : JpaRepository<UserEntity, UUID> {

    fun findOneByName(name: String): Optional<UserEntity>

    fun findOneByEmail(email: String): Optional<UserEntity>

}