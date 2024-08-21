package com.financesbox.usermgmt.domain.user.exception

import com.financesbox.shared.domain.exception.EntityNotFoundDomainException

class UserNotFoundDomainException(message: String) : EntityNotFoundDomainException(message)