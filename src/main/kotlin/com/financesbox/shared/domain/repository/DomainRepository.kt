package com.financesbox.shared.domain.repository

import com.financesbox.shared.domain.model.DomainModel
import java.util.*

interface DomainRepository<M : DomainModel, ID> {

    fun save(model: M): M

    fun update(model: M): M

    fun findById(id: ID): Optional<M>

}