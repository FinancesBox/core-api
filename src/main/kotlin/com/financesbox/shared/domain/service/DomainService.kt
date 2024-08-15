package com.financesbox.shared.domain.service

import jakarta.validation.Valid

interface DomainService<I, O> {

    fun execute(@Valid dto: I): O

}