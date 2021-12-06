package com.github.hugovallada.errors

import com.github.hugovallada.errors.customErrors.AlreadyFollowingException
import com.github.hugovallada.errors.customErrors.NotFoundException
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper
import javax.ws.rs.BadRequestException

class ErrorHandler {

    @ServerExceptionMapper(value = [NotFoundException::class])
    fun handleNotFoundException(ex: NotFoundException): RestResponse<ErrorResponse> {
        return RestResponse.status(
            RestResponse.Status.NOT_FOUND,
            ErrorResponse("404", ex.message ?: "Not Found")
        )
    }

    @ServerExceptionMapper(value = [AlreadyFollowingException::class])
    fun handleAlreadyFollowingException(ex: AlreadyFollowingException) : RestResponse<ErrorResponse> {
        return RestResponse.status(
            RestResponse.Status.CONFLICT,
            ErrorResponse("409", ex.message ?: "User is already following")
        )
    }

    @ServerExceptionMapper(value = [BadRequestException::class])
    fun handleBadRequestException(ex: BadRequestException) : RestResponse<ErrorResponse> {
        return RestResponse.status(
            RestResponse.Status.BAD_REQUEST,
            ErrorResponse("400", ex.message ?: "Bad Request")
        )
    }

}