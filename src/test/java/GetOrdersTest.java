import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.*;

public class GetOrdersTest extends BaseTest {

    public static String generatedString = RandomStringUtils.random(20, false, true);
    public static String email = generatedString.concat("oom@gmail.com");

    public static User user1 = new User(email, password, name);
    public static String tokenUser1;

    @BeforeClass
    public static void initData() {
        tokenUser1 = userClient.getToken(user1);
    }

    @AfterClass
    public static void tearDown() {
        userClient.deleteCurrentUser(tokenUser1);
    }

    @Test
    @DisplayName("Test that it is possible to get a list of orders created by authorised user")
    public void getAuthUserOrders() {
        orderClient.getOrders(tokenUser1)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("orders", instanceOf(ArrayList.class));
    }

    @Test
    @DisplayName("Test that it is not possible to get a list of orders created by unauthorised user")
    public void getNotAuthUserOrders() {
        orderClient.getOrders("")
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}