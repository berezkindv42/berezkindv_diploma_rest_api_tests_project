package tests;

import io.qameta.allure.Owner;
import io.restassured.response.Response;
import models.LombokModel;
import models.Resource;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.TestData.*;
import static specs.Specs.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests extends TestBase {

    @Test
    @Tag("api")
    @DisplayName("User list test")
    @Owner("berezkindv")
    void userListTest() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec200)
                .extract().response();

        assertThat(response).isNotNull();
        assertEquals(12, (Integer) response.path("total"));
        assertEquals(2, (Integer) response.path("page"));
    }

    @Test
    @Tag("api")
    @DisplayName("Single user test")
    @Owner("berezkindv")
    void singleUserTest() {
        LombokModel response = given()
                .spec(requestSpec)
                .when()
                .get("/users/10")
                .then()
                .spec(responseSpec200)
                .extract().as(LombokModel.class);

        Integer expectedId = 10;
        String expectedFirstName = "Byron";
        String expectedLastName = "Fields";

        assertEquals(expectedId, response.getUser().getId());
        assertEquals(expectedFirstName, response.getUser().getFirstName());
        assertEquals(expectedLastName, response.getUser().getLastName());
    }

    @Test
    @Tag("api")
    @DisplayName("Single user not found test")
    @Owner("berezkindv")
    void singleUserNotFoundTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/13")
                .then()
                .spec(responseSpec404);
    }

    @Test
    @Tag("api")
    @DisplayName("Resources list test")
    @Owner("berezkindv")
    void resourceListTest() {
        Response response =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(responseSpec200)
                        .extract().response();

        Integer expectedTotal = 12;

        assertThat(response).isNotNull();
        assertEquals(expectedTotal, response.path("total"));
    }

    // СДЕЛЯЛЬ ПО СЮДА!!!11

    @Test
    @Tag("api")
    @DisplayName("Single resources list test")
    @Owner("berezkindv")
    void singleResourceTest() {

        Resource responseResource =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/unknown/4")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(Resource.class);

        String expectedName = "aqua sky";
        Integer expectedYear = 2003;
        String expectedColor = "#7BC4C4";
        String expectedPantoneValue = "14-4811";

//        assertEquals(expectedName, responseResource.getName());
//        assertEquals(expectedYear, responseResource.getYear());
//        assertEquals(expectedColor, responseResource.getColor());
//        assertEquals(expectedPantoneValue, responseResource.getPantoneValue());


//                        .body("data.name", is("fuchsia rose"))
//                        .body("data.color", is("#C74375"));
    }

    @Test
    @Tag("api")
    @DisplayName("Single resources not found test")
    @Owner("berezkindv")
    void singleResourceNotFoundTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseSpec404);
    }

    @Test
    @Tag("api")
    @DisplayName("Create user test")
    @Owner("berezkindv")
    void createUserTest() {
        given()
                .spec(requestSpec)
                .body(createUserData)
                .when()
                .post("/user")
                .then()
                .spec(responseSpec201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue());
    }

    @Test
    @Tag("api")
    @DisplayName("Update user test with PUT request")
    @Owner("berezkindv")
    void updateUserPutTest() {
        given()
                .spec(requestSpec)
                .body(updateUserData)
                .when()
                .put("/user/2")
                .then()
                .spec(responseSpec200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @Tag("api")
    @DisplayName("Update user test with PATCH request")
    @Owner("berezkindv")
    void updateUserPatchTest() {
        given()
                .spec(requestSpec)
                .body(updateUserData)
                .when()
                .patch("/user/2")
                .then()
                .spec(responseSpec200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @Tag("api")
    @DisplayName("Delete user test")
    @Owner("berezkindv")
    void deleteUserTest() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/user/2")
                .then()
                .spec(responseSpec204);
    }

    @Test
    @Tag("api")
    @DisplayName("Successful register user test")
    @Owner("berezkindv")
    void registerSuccessfulTest() {
        given()
                .spec(requestSpec)
                .body(successfulRegisterUser)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @Tag("api")
    @DisplayName("Unsuccessful register user test")
    @Owner("berezkindv")
    void registerUnsuccessfulTest() {
        given()
                .spec(requestSpec)
                .body(unsuccessfulRegisterUser)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec400)
                .body("error", is("Missing password"));
    }

    //    @Test
//    @DisplayName("Успешная авторизация пользователя")
//    void successfulLogin() {
//
//        user.setEmail("eve.holt@reqres.in");
//        user.setPassword("cityslicka");
//
//        User response = given()
//                .spec(request)
//                .body(user)
//                .when()
//                .post("/login")
//                .then()
//                .spec(responseSpec200)
//                .extract().as(User.class);
//
//        assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
//    }
    @Test
    @Tag("api")
    @DisplayName("Successful login user test")
    @Owner("berezkindv")
    void loginSuccessfulTest() {
        Response response = given()
                .spec(requestSpec)
                .body(successfulLoginUser)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec200)
                .extract().response();

        String token = response.path("token");
        String expectedToken = "QpwL5tke4Pnpja7X4";

        assertEquals(expectedToken, token);
        assertThat(token).isNotNull();
    }
}
