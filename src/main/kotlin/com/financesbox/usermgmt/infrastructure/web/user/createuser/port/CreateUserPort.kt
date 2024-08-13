package com.financesbox.usermgmt.infrastructure.web.user.createuser.port

import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserRequestModel

interface CreateUserPort {

    suspend fun createUser(request: CreateUserRequestModel)

}