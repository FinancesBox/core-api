package com.financesbox.shared.application.cqs.command

import com.financesbox.shared.domain.event.Event

interface CommandHandler<C : Command<E>, E : Event> {

    suspend fun handle(command: C): E

}