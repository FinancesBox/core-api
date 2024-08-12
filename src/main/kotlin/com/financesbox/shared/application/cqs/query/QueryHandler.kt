package com.financesbox.shared.application.cqs.query

interface QueryHandler<Q : Query<R>, R : QueryModel> {

    suspend fun handle(query: Q): R

}