package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.UnsupportedOperationDomainException

class UserAlreadyExistsDomainExceptionUnsupported(message: String = "User already exists") :
    UnsupportedOperationDomainException(message)