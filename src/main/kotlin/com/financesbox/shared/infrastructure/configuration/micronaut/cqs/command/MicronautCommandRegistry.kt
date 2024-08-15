package com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command

import com.financesbox.shared.application.cqs.command.Command
import com.financesbox.shared.application.cqs.command.CommandHandler
import com.financesbox.shared.domain.event.Event
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.reflect.GenericTypeUtils
import jakarta.annotation.PostConstruct
import jakarta.inject.Singleton

@Singleton
@Requires(property = "command.implementation", value = "MICRONAUT")
class MicronautCommandRegistry(private val context: ApplicationContext) {

    private val registry: MutableMap<Class<Command<*>>, CommandHandler<*, *>> = mutableMapOf()

    @PostConstruct
    fun init() {
        val beanDefinitions = context.getBeanDefinitions(CommandHandler::class.javaObjectType)
        beanDefinitions.forEach { beanDefinition ->
            this.registry[GenericTypeUtils.resolveInterfaceTypeArguments(
                beanDefinition.beanType, CommandHandler::class.javaObjectType
            )[0] as Class<Command<*>>] = context.getBean(beanDefinition.beanType)
        }
    }

    fun <E : Event, C : Command<E>> getHandler(command: C): CommandHandler<C, E> {
        @Suppress("UNCHECKED_CAST") return this.registry[command.javaClass] as CommandHandler<C, E>
    }

}