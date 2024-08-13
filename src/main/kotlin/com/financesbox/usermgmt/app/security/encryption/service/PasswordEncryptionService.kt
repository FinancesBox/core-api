package com.financesbox.usermgmt.app.security.encryption.service

interface PasswordEncryptionService {

    fun hashPassword(password: String): String

    fun checkPassword(password: String, hashed: String): Boolean

}