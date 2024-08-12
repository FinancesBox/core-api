package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.query

import com.financesbox.shared.application.cqs.query.Query
import com.financesbox.shared.application.cqs.query.QueryHandler
import com.financesbox.shared.application.cqs.query.QueryModel
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.reflect.GenericTypeUtils
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Requires(property = "query.implementation", value = "MICRONAUT")
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