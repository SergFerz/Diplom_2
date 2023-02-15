package api;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateOrderRequest;

import static api.Api.*;
import static io.restassured.RestAssured.given;

public class OrderClient {

    private RequestSpecification specification;

    public OrderClient(RequestSpecification specification) {
        this.specification = specification;
    }

    public Response createOrder(CreateOrderRequest request, String token) {
        return given().spec(specification)
                .body(request)
                .header("Authorization", token)
                .when()
                .post(ORDER_DATA.path);
    }

    public Response createOrder(String token) {
        return given().spec(specification)
                .body("")
                .header("Authorization", token)
                .when()
                .post(ORDER_DATA.path);
    }

    public Response getOrders(String token) {
        return given().spec(specification)
                .header("Authorization", token)
                .get(ORDER_DATA.path);
    }

    public Response getIngredient() {
        return given().spec(specification)
                .get(INGREDIENT.path);
    }
}
