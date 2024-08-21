package com.financesbox.usermgmt.infrastructure.web.user.getuser.adapter

import com.financesbox.shared.application.cqs.query.QueryBus
import com.financesbox.shared.infrastructure.configuration.micronaut.api.API.API_V1_URI
import com.financesbox.usermgmt.app.user.query.getuser.GetUserQuery
import com.financesbox.usermgmt.infrastructure.web.shared.api.UserManagementAPI.SWAGGER_TAG
import com.financesbox.usermgmt.infrastructure.web.user.getuser.model.UserResponseModel
import com.financesbox.usermgmt.infrastructure.web.user.getuser.port.GetUserPort
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.*

@Controller(API_V1_URI)
@Tag(name = SWAGGER_TAG)
class GetUserHttpAdapter(private val queryBus: QueryBus) : GetUserPort {

    @Get("/users/{id}")
    @Status(HttpStatus.OK)
    @Operation(summary = "Get a user")
    @ApiResponse(
        responseCode = "200", description = "User retrieved", content = [Content(
            mediaType = MediaType.APPLICATION_JSON, schema = Schema(implementation = UserResponseModel::class)
        )]
    )
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    override fun getUser(id: UUID): UserResponseModel {
        val queryModel = queryBus.syncExecute(GetUserQuery(id))
        return UserResponseModel(
            id = queryModel.id,
            name = queryModel.name,
            email = queryModel.email,
            roles = queryModel.roles,
            createdAt = queryModel.createdAt
        )
    }

}