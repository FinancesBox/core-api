package com.financesbox.usermgmt.domain.user.event

import com.financesbox.shared.domain.event.Event
import com.financesbox.usermgmt.domain.user.model.UserRole
import java.util.*

class UserCreatedEvent(
    val id: UUID, val name: String, val email: String, val password: String, val roles: List<UserRole>
) : Event()