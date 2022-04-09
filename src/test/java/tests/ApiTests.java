package tests;

import config.CredentialsConfig;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import models.LombokModel;
import models.Resource;
import models.User;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static specs.Specs.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests extends TestBase {

    CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    @Test
    @Tag("api")
    @DisplayName("User list test")
    @Owner("berezkindv")
    void userListTest() {
        Response response =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(responseSpec200)
                        .extract().response();

        assertThat(response).isNotNull();
        assertThat(matchesJsonSchemaInClasspath("schemas/user_list_response_schema.json"));
        assertEquals(12, (Integer) response.path("total"));
        assertEquals(2, (Integer) response.path("page"));
    }

    @Test
    @Tag("api")
    @DisplayName("Single user test")
    @Owner("berezkindv")
    void singleUserTest() {
        LombokModel response =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users/10")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(LombokModel.class);
        Integer expectedId = 10;
        String expectedFirstName = "Byron";
        String expectedLastName = "Fields";

        assertThat(matchesJsonSchemaInClasspath("schemas/single_user_response_schema.json"));
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
        assertThat(matchesJsonSchemaInClasspath("schemas/resource_list_response_schema.json"));
        assertEquals(expectedTotal, response.path("total"));
    }

    @Test
    @Tag("api")
    @DisplayName("Single resources test")
    @Owner("berezkindv")
    void singleResourceTest() {
        Response response =
                given()
                        .spec(requestSpec)
                        .when().get("/unknown/4")
                        .then()
                        .spec(responseSpec200)
                        .extract().response();
        String expectedName = "aqua sky";
        Integer expectedYear = 2003;
        String expectedColor = "#7BC4C4";
        String expectedPantoneValue = "14-4811";

        assertThat(matchesJsonSchemaInClasspath("schemas/single_resource_response_schema.json"));
        assertEquals(expectedName, response.path("data.name"));
        assertEquals(expectedYear, response.path("data.year"));
        assertEquals(expectedColor, response.path("data.color"));
        assertEquals(expectedPantoneValue, response.path("data.pantone_value"));
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
    void createResourceTest() {
        resource.setName("orange sun");
        resource.setYear(2022);
        resource.setColor("#FF5800");
        resource.setPantoneValue("16-1257");
        Resource responseResource =
                given()
                        .spec(requestSpec)
                        .body(resource)
                        .when()
                        .post("/unknown")
                        .then()
                        .spec(responseSpec201)
                        .extract().as(Resource.class);

        assertNotEquals(responseResource.getId(), null);
        assertEquals(resource.getName(), responseResource.getName());
        assertEquals(resource.getYear(), responseResource.getYear());
        assertEquals(resource.getColor(), responseResource.getColor());
        assertEquals(resource.getPantoneValue(), responseResource.getPantoneValue());
    }

    @Test
    @Tag("api")
    @DisplayName("Create user test")
    @Owner("berezkindv")
    void createUserTest() {
        user.setFirstName(config.createUserName());
        user.setJob(config.createUserJob());
        User responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/user")
                        .then()
                        .spec(responseSpec201)
                        .extract().as(User.class);

        assertNotEquals(responseUser.getId(), null);
        assertEquals(user.getFirstName(), responseUser.getFirstName());
        assertEquals(user.getJob(), responseUser.getJob());
    }

    @Test
    @Tag("api")
    @DisplayName("Update user test with PUT request")
    @Owner("berezkindv")
    void updateUserPutTest() {
        user.setFirstName(config.createUserName());
        user.setJob(config.updateUserJob());
        User responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .put("/user/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(User.class);

        assertEquals(user.getJob(), responseUser.getJob());
    }

    @Test
    @Tag("api")
    @DisplayName("Update user test with PATCH request")
    @Owner("berezkindv")
    void updateUserPatchTest() {
        user.setFirstName(config.createUserName());
        user.setJob(config.updateUserJob());
        User responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .patch("/user/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(User.class);

        assertEquals(user.getJob(), responseUser.getJob());
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
        user.setEmail(config.registerUserEmail());
        user.setPassword(config.registerUserPassword());
        User responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(User.class);

        assertThat(responseUser.getId()).isNotNull();
        assertThat(responseUser.getToken()).isNotNull();
    }

    @Test
    @Tag("api")
    @DisplayName("Unsuccessful register user test")
    @Owner("berezkindv")
    void registerUnsuccessfulTest() {
        user.setEmail("sydney@fife");
        User responseUser =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec400)
                        .extract().as(User.class);
        String expectedError = "Missing password";

        assertEquals(expectedError, responseUser.getError());
    }

    @Test
    @Tag("api")
    @DisplayName("Successful login user test")
    @Owner("berezkindv")
    void loginSuccessfulTest() {
        user.setEmail(config.registerUserEmail());
        user.setPassword(config.loginUserPassword());
        User responseUser = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec200)
                .extract().as(User.class);

        assertThat(responseUser.getToken()).isNotNull();

    }

    @Test
    @Tag("api")
    @DisplayName("Successful login user test")
    @Owner("berezkindv")
    void loginUnsuccessfulTest() {
        user.setEmail("peter@klaven");
        User responseUser = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec400)
                .extract().as(User.class);
        String expectedError = "Missing password";

        assertEquals(expectedError, responseUser.getError());
    }
}
