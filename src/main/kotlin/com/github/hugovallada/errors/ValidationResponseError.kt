package com.github.hugovallada.errors

data class ValidationResponseError(
    val message: String,
    val errors: Collection<Any>
)
