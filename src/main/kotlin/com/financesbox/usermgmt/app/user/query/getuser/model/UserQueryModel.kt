package com.financesbox.usermgmt.app.user.query.getuser.model

import com.financesbox.shared.application.cqs.query.QueryModel
import io.micronaut.serde.annotation.Serdeable
import java.time.Instant
import java.util.*

@Serdeable
class UserQueryModel(
    val id: UUID, val name: String, val email: String, val roles: List<String>, val createdAt: Instant
) : QueryModel()