package com.financesbox.core.micronaut.cqs.query

import com.financesbox.core.cqs.query.Query
import com.financesbox.core.cqs.query.QueryBus
import com.financesbox.core.cqs.query.QueryModel
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.*

@Singleton
@OptIn(DelicateCoroutinesApi::class)
@Requires(property = "query.implementation", value = "MICRONAUT")
class MicronautQueryBus(
    @Value("\${micronaut.query.timeoutInMillis}") private val timeout: Long,
    @Inject private val registry: MicronautQueryRegistry
) : QueryBus {

    override fun <E : QueryModel, C : Query<E>> asyncExecute(query: C): Deferred<E> = GlobalScope.async {
        registry.getHandler(query).handle(query)
    }

    override suspend fun <E : QueryModel, C : Query<E>> syncExecute(query: C): E {
        return withTimeout(timeout) {
            asyncExecute(query).await()
        }
    }

}