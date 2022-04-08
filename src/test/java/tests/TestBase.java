package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import models.Resource;
import models.User;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    User user = new User();
    Resource resource = new Resource();

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        Configuration.baseUrl = "https://reqres.in";
    }
}
