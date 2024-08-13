package com.financesbox.shared.infrastructure.persistence.jpa.entity

import java.time.Instant

interface BaseEntity {
    val createdAt: Instant
    val updatedAt: Instant
}