package com.financesbox.shared.infrastructure.configuration.micronaut.event

import com.financesbox.shared.application.event.EventListener
import com.financesbox.shared.domain.event.Event
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.reflect.GenericTypeUtils
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
@Requires(property = "event.implementation", value = "MICRONAUT")
class MicronautEventBroker(@Inject private val context: ApplicationContext) {

    private val registry: MutableMap<Class<Event>, MutableList<EventListener<*>>> = mutableMapOf()

    @PostConstruct
    fun init() {
        val beanDefinitions = context.getBeanDefinitions(EventListener::class.javaObjectType)
        beanDefinitions.forEach { beanDefinition ->
            val eventClass = GenericTypeUtils.resolveInterfaceTypeArguments(
                beanDefinition.beanType, EventListener::class.javaObjectType
            )[0] as Class<Event>
            if (this.registry[eventClass] != null && this.registry[eventClass]!!.isNotEmpty()) {
                this.registry[eventClass]!!.add(context.getBean(beanDefinition.beanType))
            } else {
                this.registry[eventClass] = mutableListOf(context.getBean(beanDefinition.beanType))
            }
        }
    }

    suspend fun <E : Event> dispatch(event: E) {
        this.registry[event.javaClass]?.forEach { listener ->
            withContext(Dispatchers.Default) {
                (listener as EventListener<E>).receive(event)
            }
        }
    }

}