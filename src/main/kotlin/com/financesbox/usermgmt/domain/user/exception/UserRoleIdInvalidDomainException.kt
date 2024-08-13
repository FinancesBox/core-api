package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.InvalidInputDomainException

class UserRoleIdInvalidDomainException(message: String = "User role ID is invalid") :
    InvalidInputDomainException(message)