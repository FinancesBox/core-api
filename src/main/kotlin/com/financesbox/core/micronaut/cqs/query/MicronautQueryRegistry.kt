package com.financesbox.core.micronaut.cqs.query

import com.financesbox.core.cqs.query.Query
import com.financesbox.core.cqs.query.QueryHandler
import com.financesbox.core.cqs.query.QueryModel
import io.micronaut.context.ApplicationContext
import io.micronaut.core.reflect.GenericTypeUtils
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class MicronautQueryRegistry(@Inject private val context: ApplicationContext) {

    private val registry: MutableMap<Class<Query<*>>, QueryHandler<*, *>> = mutableMapOf()

    @PostConstruct
    fun init() {
        val beanDefinitions = context.getBeanDefinitions(QueryHandler::class.javaObjectType)
        beanDefinitions.forEach { beanDefinition ->
            this.registry[GenericTypeUtils.resolveInterfaceTypeArguments(
                beanDefinition.beanType, QueryHandler::class.javaObjectType
            )[0] as Class<Query<*>>] = context.getBean(beanDefinition.beanType)
        }
    }

    fun <R : QueryModel, Q : Query<R>> getHandler(query: Q): QueryHandler<Q, R> {
        @Suppress("UNCHECKED_CAST") return this.registry[query.javaClass] as QueryHandler<Q, R>
    }

}