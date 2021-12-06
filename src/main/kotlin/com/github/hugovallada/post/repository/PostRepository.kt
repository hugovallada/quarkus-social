package com.github.hugovallada.post.repository

import com.github.hugovallada.post.dto.PostResponse
import com.github.hugovallada.post.model.Post
import com.github.hugovallada.user.model.User
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PostRepository : PanacheRepository<Post> {

    fun getPostsFromUser(user: User): List<PostResponse> = find("user", user)
        .project(PostResponse::class.java).list()

}