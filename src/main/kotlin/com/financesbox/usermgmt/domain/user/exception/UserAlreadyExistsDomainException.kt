package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.OperationNotAllowedDomainException

class UserAlreadyExistsDomainException(message: String = "User already exists") :
    OperationNotAllowedDomainException(message)