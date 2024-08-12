package com.financesbox.core.cqs.command

import com.financesbox.core.event.Event

interface CommandHandler<C : Command<E>, E : Event> {

    suspend fun handle(command: C): E

}