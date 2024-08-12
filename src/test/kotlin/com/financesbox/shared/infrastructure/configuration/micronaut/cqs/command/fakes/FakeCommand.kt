package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command.fakes

import com.financesbox.shared.application.cqs.command.Command

class FakeCommand(val name: String, val delay: Long) :
    Command<FakeEvent>()