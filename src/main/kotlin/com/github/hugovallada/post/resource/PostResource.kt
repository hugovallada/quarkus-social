package com.github.hugovallada.post.resource

import com.github.hugovallada.errors.customErrors.NotFoundException
import com.github.hugovallada.post.dto.CreatePostRequest
import com.github.hugovallada.post.dto.PostResponse
import com.github.hugovallada.post.model.Post
import com.github.hugovallada.post.repository.PostRepository
import com.github.hugovallada.user.repository.UserRepository
import org.jboss.logging.Logger
import org.jboss.resteasy.reactive.RestResponse
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PostResource(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val log: Logger
) {

    @POST
    @Transactional
    fun savePost(@PathParam("userId") userId: Long, request: CreatePostRequest) : RestResponse<Any> {
        userRepository.findById(userId)?.run {
            log.info(request)
            val post = Post(text = request.text, user = this)
            postRepository.persist(post)
            return RestResponse.status(Response.Status.CREATED, post)
        }
        return RestResponse.status(404, "Usuário não encontrado")
    }

    @GET
    fun getPosts(@PathParam("userId") userId: Long) :RestResponse<List<PostResponse>>{
        userRepository.findById(userId)?.run {
            return RestResponse.ok(postRepository.getPostsFromUser(this))
        } ?: throw NotFoundException("Usuário com id $userId não encontrado")
    }


}