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
}