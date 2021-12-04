package com.github.hugovallada.user.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class CreateUserRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 30)
    val name: String,

    @field:NotNull
    @field:Positive
    val age: Int
)
