package com.financesbox.usermgmt.infrastructure.security.encryption

import com.financesbox.usermgmt.app.security.encryption.service.PasswordEncryptionService
import jakarta.inject.Singleton
import org.mindrot.jbcrypt.BCrypt

@Singleton
class BCryptPasswordEncryptionService : PasswordEncryptionService {

    override fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun checkPassword(password: String, hashed: String): Boolean {
        return BCrypt.checkpw(password, hashed)
    }

}