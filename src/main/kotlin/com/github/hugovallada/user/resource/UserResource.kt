package com.github.hugovallada.user.resource

import com.github.hugovallada.user.dto.CreateUserRequest
import com.github.hugovallada.user.model.User
import com.github.hugovallada.user.repository.UserRepository
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserResource(private val userRepository: UserRepository) {

    @POST
    @Transactional
    fun createUser(userRequest: CreateUserRequest) : Response {
        val user = User(name = userRequest.name, age = userRequest.age)
        userRepository.persist(user)
        return Response.status(201).build()
    }

    @GET
    fun listAllUsers() : Response {
        return Response.ok(userRepository.listAll()).build()
    }

    @PUT
    @Path("{userId}")
    @Transactional
    fun updateUser(@PathParam("userId") userId: Long, userRequest: CreateUserRequest) : Response {
        userRepository.findById(userId)?.apply {
            age = userRequest.age
            name = userRequest.name
            // userRepository.persist(this) - O Transactional vai fazer o update ao fim do commit
            return Response.accepted().build()
        }
        return Response.status(404, "User not found!").build()
    }

    @DELETE
    @Path("{userId}")
    @Transactional
    fun deleteUser(@PathParam("userId") userId: Long) : Response {
        userRepository.findById(userId)?.apply {
            userRepository.delete(this)
            return Response.noContent().build()
        }
        return Response.status(404, "User not found!").build()
    }
}