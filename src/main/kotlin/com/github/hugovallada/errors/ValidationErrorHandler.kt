package com.github.hugovallada.errors

import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.ws.rs.core.Response

class ValidationErrorHandler {

    @ResponseStatus(400)
    @ServerExceptionMapper(value = [ConstraintViolationException::class])
    fun handleConstraintViolationError(ex: ConstraintViolationException): RestResponse<ValidationResponseError> {
        val errors = mutableListOf<Map<String, String>>()

        ex.constraintViolations.forEach {
            errors.add(buildErrorMessage(it))
        }

        return RestResponse.status(Response.Status.BAD_REQUEST, ValidationResponseError("Erros de validação! Corrija antes de continuar.", errors))
    }

    private fun buildErrorMessage(constraint: ConstraintViolation<*>): Map<String, String> {
        return mapOf<String, String>(
            "Type Of Error" to getPropertyName(constraint),
            "Expected" to getErrorMessage(constraint),
            "Was" to getBadValue(constraint)
        )
    }

    private fun getPropertyName(constraint: ConstraintViolation<*>): String {
        return constraint.messageTemplate.toString().split("constraints.")
            .last().split(".")[0]
    }

    private fun getBadValue(constraint: ConstraintViolation<*>): String {
        return constraint.invalidValue.toString()
    }

    private fun getErrorMessage(constraint: ConstraintViolation<*>): String {
        return constraint.message
    }

}