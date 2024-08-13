package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.InvalidInputDomainException

class UserRolesMismatchDomainException(message: String = "User roles do not mismatch") :
    InvalidInputDomainException(message)