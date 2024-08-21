package com.financesbox.usermgmt.infrastructure.persistence.user.entity

import com.financesbox.shared.infrastructure.persistence.entity.BaseEntity
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id val id: UUID,
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "email", nullable = false) val email: String,
    @Column(name = "password", nullable = false) val password: String,
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    ) val roles: List<UserRoleEntity>,
    @Column(name = "created_at", nullable = false) override val createdAt: Instant,
    @Column(name = "updated_at", nullable = false) override val updatedAt: Instant,
) : BaseEntity