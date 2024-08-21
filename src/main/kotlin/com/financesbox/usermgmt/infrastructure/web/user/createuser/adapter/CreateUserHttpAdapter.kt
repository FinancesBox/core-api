package com.financesbox.usermgmt.infrastructure.web.user.createuser.adapter

import com.financesbox.shared.application.cqs.command.CommandBus
import com.financesbox.shared.infrastructure.configuration.micronaut.api.API.API_V1_URI
import com.financesbox.usermgmt.app.user.command.createuser.CreateUserCommand
import com.financesbox.usermgmt.infrastructure.web.shared.api.UserManagementAPI.SWAGGER_TAG
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserRequestModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserResponseModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.port.CreateUserPort
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Controller(API_V1_URI)
@Tag(name = SWAGGER_TAG)
class CreateUserHttpAdapter(private val commandBus: CommandBus) : CreateUserPort {

    @Post("/users")
    @Status(HttpStatus.CREATED)
    @Operation(summary = "Create a new user")
    @ApiResponse(
        responseCode = "201",
        description = "User created",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = Schema(implementation = CreateUserResponseModel::class)
        )]
    )
    @ApiResponse(responseCode = "409", description = "User already exists")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    override fun createUser(@Body @Valid request: CreateUserRequestModel): CreateUserResponseModel {
        val event = commandBus.syncExecute(
            CreateUserCommand(
                name = request.name, email = request.email, password = request.password, roles = request.roles
            )
        )
        return CreateUserResponseModel(
            userId = event.id
        )
    }

}