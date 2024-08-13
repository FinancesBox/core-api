package com.financesbox.shared.infrastructure.persistence.mapper

import com.financesbox.shared.domain.model.DomainModel
import com.financesbox.shared.infrastructure.persistence.jpa.entity.BaseEntity

interface Mapper<E : BaseEntity, D : DomainModel> {

    fun toDomain(entity: E): D

    fun toEntity(domainModel: D): E

}