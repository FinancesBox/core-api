package com.financesbox.core.micronaut.cqs.query.fakes

import com.financesbox.core.cqs.query.Query

class FakeQuery(val name: String, val delay: Long) : Query<FakeQueryModel>()