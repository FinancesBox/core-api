package com.financesbox.usermgmt.infrastructure.web.user.createuser.port

import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserRequestModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserResponseModel

interface CreateUserPort {

    fun createUser(request: CreateUserRequestModel): CreateUserResponseModel

}