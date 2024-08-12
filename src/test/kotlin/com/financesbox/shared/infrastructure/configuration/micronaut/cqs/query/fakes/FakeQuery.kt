package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.query.fakes

import com.financesbox.shared.application.cqs.query.Query

class FakeQuery(val name: String, val delay: Long) :
    Query<FakeQueryModel>()