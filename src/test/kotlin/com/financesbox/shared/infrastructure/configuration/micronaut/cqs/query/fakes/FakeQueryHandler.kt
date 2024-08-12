package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.query.fakes

import com.financesbox.shared.application.cqs.query.QueryHandler
import jakarta.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
class FakeQueryHandler :
    QueryHandler<FakeQuery, FakeQueryModel> {

    override suspend fun handle(query: FakeQuery): FakeQueryModel {
        delay(query.delay)
        return FakeQueryModel(query.name)
    }

}