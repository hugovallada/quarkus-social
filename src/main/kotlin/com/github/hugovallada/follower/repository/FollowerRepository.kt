package com.github.hugovallada.follower.repository

import com.github.hugovallada.follower.model.Follower
import com.github.hugovallada.user.model.User
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FollowerRepository : PanacheRepository<Follower> {

    fun isAlreadyFollowed(follower: User, followed: User) = find("user = ?1 and follower = ?2", followed, follower)
        .count() > 0
}