package com.github.hugovallada.follower.dto

import com.github.hugovallada.follower.model.Follower

data class FollowersResponse(
    val followers: Set<Follower>,
    val count: Int
)
