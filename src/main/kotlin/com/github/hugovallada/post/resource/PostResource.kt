package com.github.hugovallada.post.resource

import com.github.hugovallada.errors.customErrors.NotFoundException
import com.github.hugovallada.follower.repository.FollowerRepository
import com.github.hugovallada.post.dto.CreatePostRequest
import com.github.hugovallada.post.dto.PostResponse
import com.github.hugovallada.post.model.Post
import com.github.hugovallada.post.repository.PostRepository
import com.github.hugovallada.user.repository.UserRepository
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
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
    private val followerRepository: FollowerRepository,
    private val log: Logger
) {

    @POST
    @Transactional
    fun savePost(@PathParam("userId") userId: Long, request: CreatePostRequest) : RestResponse<Post> {
        userRepository.findById(userId)?.run {
            log.info(request)
            val post = Post(text = request.text, user = this)
            postRepository.persist(post)
            return RestResponse.status(Response.Status.CREATED, post)
        } ?: throw NotFoundException("Usuário com id $userId não encontrado")
    }

    @GET
    @Path("/{followerId}")
    fun getPosts(@PathParam("userId") userId: Long, @PathParam("followerId") followerId: Long) :RestResponse<List<PostResponse>>{
        if (!followerRepository.isAlreadyFollowed(followerId, userId)) {
            throw BadRequestException("You must follow the user to see this post!")
        }
        userRepository.findById(userId)?.run {
            return RestResponse.ok(postRepository.getPostsFromUser(this))
        } ?: throw NotFoundException("Usuário com id $userId não encontrado")
    }


}