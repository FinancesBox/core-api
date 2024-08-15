package com.financesbox.usermgmt.app.user.command.createuser

import com.financesbox.shared.application.cqs.command.Command
import com.financesbox.usermgmt.domain.user.event.UserCreatedEvent

class CreateUserCommand(val name: String, val email: String, val password: String, val roles: List<String>) :
    Command<UserCreatedEvent>()