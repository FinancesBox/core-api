package com.financesbox.usermgmt.infrastructure.web.user.getuser.port

import com.financesbox.usermgmt.infrastructure.web.user.getuser.model.UserResponseModel
import java.util.*

interface GetUserPort {

    fun getUser(id: UUID): UserResponseModel

}