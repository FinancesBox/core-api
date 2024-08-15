package com.financesbox.shared.domain.service

interface DomainService<I, O> {

    fun execute(dto: I): O

}