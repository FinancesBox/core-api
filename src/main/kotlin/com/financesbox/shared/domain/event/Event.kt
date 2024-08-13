package com.financesbox.shared.domain.event

import io.micronaut.serde.annotation.Serdeable
import java.time.Instant

@Serdeable
open class Event(val timestamp: Instant = Instant.now())