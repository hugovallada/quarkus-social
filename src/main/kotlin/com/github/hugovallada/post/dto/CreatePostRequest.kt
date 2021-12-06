package com.github.hugovallada.post.dto

import com.fasterxml.jackson.annotation.JsonCreator

// Jackson não consegue fazer um bind padrão com construtor de único argumento
data class CreatePostRequest @JsonCreator constructor(
    val text: String
)