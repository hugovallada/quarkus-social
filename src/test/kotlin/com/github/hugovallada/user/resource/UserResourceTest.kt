package com.github.hugovallada.user.resource

import com.github.hugovallada.errors.ErrorResponse
import com.github.hugovallada.user.dto.CreateUserRequest
import io.kotest.assertions.print.print
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import io.kotest.matchers.string.shouldContainIgnoringCase
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import org.junit.jupiter.api.Test


@QuarkusTest
class UserResourceTest {

    @Test
    fun `should create an user successfully`() {
        val user = CreateUserRequest("Hugo", 25)

        given()
            .contentType(JSON)
            .body(user)
            .`when`()
            .post("/users")
            .then()
            .statusCode(201)
            .extract().response().run {
                statusCode shouldBe 201
            }
    }

    @Test
    fun `should throw a bad request exception when data is invalid`() {
        given()
            .contentType(JSON)
            .body(CreateUserRequest("", -1))
            .`when`()
            .post("/users")
            .then()
            .statusCode(400)
            .and()
            .extract()
            .asPrettyString().run {
                println(this)
                this shouldContainIgnoringCase "NotBlank"
                this shouldContainIgnoringCase "Size"
                this shouldContainIgnoringCase "Positive"
            }
    }

}