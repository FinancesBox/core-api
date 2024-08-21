package com.financesbox.test.configuration

import io.github.serpro69.kfaker.Faker
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class FakerFactory {

    @Singleton
    fun faker(): Faker {
        return Faker()
    }

}