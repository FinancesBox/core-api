package com.financesbox.core.cqs.query

import kotlinx.coroutines.Deferred

interface QueryBus {

    /**
     * Executes the query asynchronously.
     *
     * @param query The query to execute.
     * @return A future representing the query model.
     */
    fun <R : QueryModel, C : Query<R>> asyncExecute(query: C): Deferred<R>

    /**
     * Executes the query synchronously, blocking until the result is available.
     *
     * @param query The query to execute.
     * @return The query model returned.
     */
    suspend fun <R : QueryModel, C : Query<R>> syncExecute(query: C): R

}