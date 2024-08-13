package com.financesbox.shared.domain.repository

import com.financesbox.shared.domain.model.DomainModel

interface DomainRepository<M : DomainModel> {

    fun save(model: M): M

    fun update(model: M): M

}