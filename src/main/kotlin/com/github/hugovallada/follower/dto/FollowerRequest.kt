package com.github.hugovallada.follower.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class FollowerRequest @JsonCreator constructor(
    val followerId : Long
)
