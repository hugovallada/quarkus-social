package com.github.hugovallada.follower.resource

import com.github.hugovallada.errors.customErrors.AlreadyFollowingException
import com.github.hugovallada.errors.customErrors.NotFoundException
import com.github.hugovallada.follower.dto.FollowerRequest
import com.github.hugovallada.follower.model.Follower
import com.github.hugovallada.follower.repository.FollowerRepository
import com.github.hugovallada.user.repository.UserRepository
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class FollowerResource(
    private val userRepository: UserRepository,
    private val followerRepository: FollowerRepository
) {

    @GET
    fun getAllFollowers(@PathParam("userId") userId: Long): RestResponse<List<Follower>> {
        userRepository.findById(userId)?.run {
            followerRepository.list("user", this).let {
                return RestResponse.ok(it)
            }
        } ?: throw NotFoundException("Usuário com id $userId não encontrado")
    }

    @PUT
    @ResponseStatus(202)
    @Transactional
    fun followUser(@PathParam("userId") userId: Long, request: FollowerRequest) {
        if (userId == request.followerId) {
            throw BadRequestException("You can't follow yourself.")
        }
        userRepository.findById(userId)?.let { toBeFollowed ->
            userRepository.findById(request.followerId)?.run {

                if (followerRepository.isAlreadyFollowed(this, toBeFollowed)) {
                    throw AlreadyFollowingException("User ${this.name} is already following ${toBeFollowed.name}")
                }

                    Follower(user = toBeFollowed, follower = this).apply {
                  followerRepository.persist(this)
                }
            } ?: throw NotFoundException("Usuário com id $userId não encontrado")
        } ?: throw NotFoundException("Usuário com id $userId não encontrado")
    }

    @DELETE
    @Path("{followedId}")
    @ResponseStatus(202)
    @Transactional
    fun stopFollowing(
        @PathParam("userId") userId: Long,
        @PathParam("followedId") followedId: Long
    ) {
        if (userId == followedId) {
            throw BadRequestException("Invalid id")
        }

        userRepository.findById(userId)?.let {
            followed ->
            userRepository.findById(followedId)?.run {
                followerRepository.stopFollowing(this, followed)
            } ?: throw NotFoundException("User with id $followedId not found.")
        } ?: throw NotFoundException("User with id $userId not found")
    }


}