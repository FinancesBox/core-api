package com.financesbox.shared.application.cqs.command

import com.financesbox.shared.domain.event.Event
import jakarta.validation.Valid

interface CommandHandler<C : Command<E>, E : Event> {

    suspend fun handle(@Valid command: C): E

}