package com.github.hugovallada.user.resource

import com.github.hugovallada.user.dto.CreateUserRequest
import com.github.hugovallada.user.model.User
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserResource {

    @POST
    @Transactional
    fun createUser(userRequest: CreateUserRequest) : Response {
        val user = User(userRequest.name, userRequest.age)
        user.persist()
        return Response.status(201).build()
    }

    @GET
    fun listAllUsers() : Response {
        return Response.ok(User.listAll()).build()
    }

    @PUT
    @Path("{userId}")
    @Transactional
    fun updateUser(@PathParam("userId") userId: Long, userRequest: CreateUserRequest) : Response {
        User.findById(userId)?.apply {
            age = userRequest.age
            name = userRequest.name
            persist()
            return Response.accepted().build()
        }

        return Response.status(404, "User not found").build()
    }

    @DELETE
    @Path("{userId}")
    @Transactional
    fun deleteUser(@PathParam("userId") userId: Long) : Response {
        User.findById(userId)?.apply { User.deleteById(userId) }
            ?: return Response.status(404, "User Not Found!").build()
        return Response.noContent().build()
    }
}