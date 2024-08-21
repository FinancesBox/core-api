package com.financesbox.usermgmt.domain.user.repository

import com.financesbox.shared.domain.repository.DomainRepository
import com.financesbox.usermgmt.domain.user.model.UserRole

interface UserRoleRepository : DomainRepository<UserRole, Int> {

    fun findByNameList(names: List<String>): List<UserRole>

}