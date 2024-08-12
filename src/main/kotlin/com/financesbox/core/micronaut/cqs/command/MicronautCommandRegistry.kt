package com.financesbox.core.micronaut.cqs.command

import com.financesbox.core.cqs.command.Command
import com.financesbox.core.cqs.command.CommandHandler
import com.financesbox.core.event.Event
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.reflect.GenericTypeUtils
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Requires(property = "command.implementation", value = "MICRONAUT")
class MicronautCommandRegistry(@Inject private val context: ApplicationContext) {

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