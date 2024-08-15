package com.financesbox.usermgmt.infrastructure.persistence.user.entity

import com.financesbox.shared.infrastructure.persistence.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "roles")
class UserRoleEntity(
    @Id val id: Int,
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "created_at", nullable = false) override val createdAt: Instant,
    @Column(name = "updated_at", nullable = false) override val updatedAt: Instant
) : BaseEntity