package com.financesbox.usermgmt.app.user.query.getuser

import com.financesbox.shared.application.cqs.query.QueryHandler
import com.financesbox.usermgmt.app.user.query.getuser.model.UserQueryModel
import com.financesbox.usermgmt.domain.user.service.getuser.GetUserDTO
import com.financesbox.usermgmt.domain.user.service.getuser.GetUserDomainService
import jakarta.inject.Singleton

@Singleton
class GetUserQueryHandler(private val domainService: GetUserDomainService) :
    QueryHandler<GetUserQuery, UserQueryModel> {

    override suspend fun handle(query: GetUserQuery): UserQueryModel {
        val user = domainService.execute(GetUserDTO(id = query.id))
        return UserQueryModel(
            id = user.id,
            name = user.name,
            email = user.email,
            roles = user.roles.map { role -> role.name },
            createdAt = user.createdAt
        )
    }

}