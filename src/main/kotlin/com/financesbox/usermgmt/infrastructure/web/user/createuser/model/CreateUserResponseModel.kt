package com.financesbox.usermgmt.infrastructure.web.user.createuser.model

import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Serdeable
data class CreateUserResponseModel(val userId: UUID)