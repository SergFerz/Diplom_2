import api.UserClient;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {
    public static ArrayList<String> usersTokens = new ArrayList<>();

    public static UserClient userClient = new UserClient(specification);

    public static String generatedString = RandomStringUtils.random(20, false, true);
    public static String emailAuth = generatedString.concat("ldfd@gmail.com");
    public static String email = generatedString.concat("gfh@gmail.com");

    static User user1 = new User(email, password, name);
    static User user2 = new User(emailAuth, password, name);
    static User user3 = new User(emailAuth, name);

    public static String tokenUser1;
    public static String tokenUser2;
    public static String tokenUser3;


    @BeforeClass
    public static void initData() {
        tokenUser1 = userClient.getToken(user1);
        tokenUser2 = userClient.getToken(user2);
        tokenUser3 = userClient.getToken(user3);
        usersTokens.add(tokenUser1);
        usersTokens.add(tokenUser2);
        usersTokens.add(tokenUser3);
    }

    @AfterClass
    public static void tearDown() {
        for (String token : usersTokens) {
            if (token != null) {
                userClient.deleteCurrentUser(token);
            }
        }
    }

    @Test
    @DisplayName("Test user account creation for incorrect user object")
    public void createUserWithoutAttribute() {
        User user = new User("", "");
        userClient.createUser(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Test user account creation without one attribute")
    public void createUserWithoutOneAttribute() {
        userClient.createUser(user3)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Test duplicate user account creation")
    public void createRepeatUser() {
        User user = new User(email, password, name);
        userClient.createUser(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("User already exists"));
    }
}