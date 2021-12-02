package com.github.hugovallada.user.resource

import com.github.hugovallada.user.dto.CreateUserRequest
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserResource {

    @POST
    fun createUser(userRequest: CreateUserRequest) : Response {
        return Response.status(201).build()
    }

    @GET
    fun listAllUsers() : Response {
        return Response.ok().build()
    }
}