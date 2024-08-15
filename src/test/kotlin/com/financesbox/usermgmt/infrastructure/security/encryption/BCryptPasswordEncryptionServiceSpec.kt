package com.financesbox.usermgmt.infrastructure.security.encryption

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class BCryptPasswordEncryptionServiceSpec(private val encryptionService: BCryptPasswordEncryptionService) : FunSpec({

    test("hashPassword should generate a different hash for the same password each time") {
        val password = "mySecurePassword"

        val hash1 = encryptionService.hashPassword(password)
        val hash2 = encryptionService.hashPassword(password)

        hash1 shouldNotBe hash2
    }

    test("checkPassword should return true for correct password and hash") {
        val password = "mySecurePassword"
        val hash = encryptionService.hashPassword(password)

        encryptionService.checkPassword(password, hash).shouldBeTrue()
    }

    test("checkPassword should return false for incorrect password") {
        val password = "mySecurePassword"
        val hash = encryptionService.hashPassword(password)
        val incorrectPassword = "wrongPassword"

        encryptionService.checkPassword(incorrectPassword, hash).shouldBeFalse()
    }

    test("hashPassword should not return the original password") {
        val password = "mySecurePassword"

        val hash = encryptionService.hashPassword(password)

        hash shouldNotBe password
    }
})
