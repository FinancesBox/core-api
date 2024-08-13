package com.financesbox.usermgmt.infrastructure.web.user.createuser.adapter.micronaut

import com.financesbox.shared.infrastructure.configuration.micronaut.cqs.command.MicronautCommandBus
import com.financesbox.usermgmt.app.user.command.usercreation.CreateUserCommand
import com.financesbox.usermgmt.infrastructure.web.user.createuser.model.CreateUserRequestModel
import com.financesbox.usermgmt.infrastructure.web.user.createuser.port.CreateUserPort
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject
import jakarta.validation.Valid

@Controller("api/v1")
@Tag(name = "Users")
class CreateUserMicronautController(@Inject private val commandBus: MicronautCommandBus) : CreateUserPort {

    @Post("user")
    @Status(HttpStatus.CREATED)
    @Operation(summary = "Create a new investor")
    @ApiResponse(responseCode = "201", description = "User created")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @ApiResponse(responseCode = "500", description = "Unexpected error")
    override suspend fun createUser(@Body @Valid request: CreateUserRequestModel) {
        commandBus.syncExecute(
            CreateUserCommand(
                name = request.name, email = request.email, password = request.password, roles = request.roles
            )
        )
    }

}