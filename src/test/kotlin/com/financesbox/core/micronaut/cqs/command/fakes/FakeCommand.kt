package com.financesbox.core.micronaut.cqs.command.fakes

import com.financesbox.core.cqs.command.Command

class FakeCommand(val name: String, val delay: Long) : Command<FakeEvent>()