package com.financesbox.usermgmt.infrastructure.persistence.user.repository

import com.financesbox.usermgmt.infrastructure.persistence.user.entity.UserRoleEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface UserRoleEntityJpaRepository : JpaRepository<UserRoleEntity, Int> {

    fun findByNameInList(names: List<String>): List<UserRoleEntity>

}