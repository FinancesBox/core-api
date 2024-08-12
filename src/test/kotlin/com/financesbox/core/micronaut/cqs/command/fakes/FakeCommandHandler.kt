package com.financesbox.core.micronaut.cqs.command.fakes

import com.financesbox.core.cqs.command.CommandHandler
import jakarta.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
class FakeCommandHandler : CommandHandler<FakeCommand, FakeEvent> {

    override suspend fun handle(command: FakeCommand): FakeEvent {
        delay(command.delay)
        return FakeEvent(command.name)
    }

}