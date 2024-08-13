package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.InvalidInputDomainException

class UserEmailInvalidDomainException(message: String = "User email is invalid") : InvalidInputDomainException(message)