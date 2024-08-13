package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.InvalidInputDomainException

class UserPasswordInvalidDomainException(message: String = "User password is invalid") :
    InvalidInputDomainException(message)