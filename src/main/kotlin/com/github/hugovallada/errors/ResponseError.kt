package com.github.hugovallada.errors

data class ResponseError(
    val message: String,
    val errors: Collection<Any>
)
