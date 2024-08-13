package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.InvalidInputDomainException

class UserRoleNameInvalidDomainException(message: String = "User role name is invalid") :
    InvalidInputDomainException(message)