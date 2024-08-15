package com.financesbox.usermgmt.domain.user.service

data class CreateUserDTO(val name: String, val email: String, val password: String, val roles: List<String>)