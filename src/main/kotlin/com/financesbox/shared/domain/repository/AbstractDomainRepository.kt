package com.financesbox.shared.domain.repository

import com.financesbox.shared.domain.model.DomainModel
import com.financesbox.shared.infrastructure.persistence.entity.BaseEntity
import com.financesbox.shared.infrastructure.persistence.mapper.Mapper
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

abstract class AbstractDomainRepository<E : BaseEntity, D : DomainModel, ID>(
    private val mapper: Mapper<E, D>,
    private val repository: JpaRepository<E, ID>,
) :
    DomainRepository<D, ID> {

    override fun save(model: D): D {
        return mapper.toDomain(repository.save(mapper.toEntity(model)))
    }

    override fun update(model: D): D {
        return mapper.toDomain(repository.update(mapper.toEntity(model)))
    }

    override fun findById(id: ID): Optional<D> {
        return checkAndReturn(repository.findById(id))
    }

    protected fun checkAndReturn(optEntity: Optional<E>): Optional<D> {
        if (optEntity.isPresent) {
            return Optional.of(mapper.toDomain(optEntity.get()))
        }
        return Optional.empty()
    }

}