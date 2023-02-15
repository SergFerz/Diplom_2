import io.qameta.allure.junit4.DisplayName;
import model.CreateOrderRequest;
import model.GetIngredientsRequest;
import model.Ingredient;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {
    public static ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static CreateOrderRequest request;

    public static String generatedString = RandomStringUtils.random(20, false, true);
    public static String email = generatedString.concat("a@gmail.com");

    public static User user1 = new User(email, password, name);
    public static String tokenUser1;

    @BeforeClass
    public static void initData() {
        tokenUser1 = userClient.getToken(user1);
    }

    @AfterClass
    public static void deleteUser() {
        userClient.deleteCurrentUser(tokenUser1);
    }

    @Before
    public void initIngredient() {
        GetIngredientsRequest ingredientsData = orderClient.getIngredient()
                .body().as(GetIngredientsRequest.class);
        ingredients = ingredientsData.getData();
        request = new CreateOrderRequest(new ArrayList<>());
        ingredients.forEach(ingredient -> request.getIngredients().add(ingredient));
    }

    @Test
    @DisplayName("Test a successful creation of order by authorized user")
    public void createOrderWithAuthTest() {
        orderClient.createOrder(request, tokenUser1)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Test creation of order without authentication")
    public void createOrderWithoutAuthTest() {
        orderClient.createOrder(request, tokenUser1)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Test creation of order without ingredients")
    public void createOrderWithoutIngredientsTest() {
        orderClient.createOrder(tokenUser1).then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Test creation of order with incorrect ingredients")
    public void createOrderWithNotValidIngredientHashTest() {
        Ingredient ingredient = new Ingredient("1234");
        request.getIngredients().add(ingredient);
        orderClient.createOrder(request, tokenUser1)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}