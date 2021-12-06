package com.github.hugovallada.post.dto

import java.time.OffsetDateTime

data class PostResponse(
    val text: String,
    val createdDate: OffsetDateTime
)