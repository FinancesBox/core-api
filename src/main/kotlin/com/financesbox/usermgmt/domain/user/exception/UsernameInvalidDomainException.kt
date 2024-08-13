package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.InvalidInputDomainException

class UsernameInvalidDomainException(message: String = "Username is invalid") : InvalidInputDomainException(message)