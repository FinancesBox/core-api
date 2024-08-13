package com.financesbox.usermgmt.domain.user.repository

import com.financesbox.shared.domain.repository.DomainRepository
import com.financesbox.usermgmt.domain.user.model.User
import java.util.*

interface UserRepository : DomainRepository<User> {

    fun findByName(name: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

}