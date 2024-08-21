package com.financesbox.usermgmt.app.user.query.getuser

import com.financesbox.shared.application.cqs.query.Query
import com.financesbox.usermgmt.app.user.query.getuser.model.UserQueryModel
import io.micronaut.core.annotation.Introspected
import jakarta.validation.constraints.NotNull
import java.util.*

@Introspected
class GetUserQuery(@NotNull val id: UUID) : Query<UserQueryModel>()