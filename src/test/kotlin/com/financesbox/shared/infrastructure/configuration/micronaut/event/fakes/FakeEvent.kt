package com.financesbox.shared.infrastructure.configuration.micronaut.event.fakes

import com.financesbox.shared.domain.event.Event

class FakeEvent(val name: String) : Event()