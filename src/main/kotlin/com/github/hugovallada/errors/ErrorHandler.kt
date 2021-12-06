package com.github.hugovallada.errors

import com.github.hugovallada.errors.customErrors.NotFoundException
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class ErrorHandler {

    @ServerExceptionMapper(value = [NotFoundException::class])
    fun handleNotFoundException(ex: NotFoundException): RestResponse<ErrorResponse> {
        return RestResponse.status(
            RestResponse.Status.NOT_FOUND,
            ErrorResponse("404", ex.message ?: "Not Found")
        )
    }

}