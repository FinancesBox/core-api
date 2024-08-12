package com.financesbox.core.cqs.query

interface QueryHandler<Q : Query<R>, R : QueryModel> {

    suspend fun handle(query: Q): R

}