package api;

import io.restassured.specification.RequestSpecification;

public class BaseClient {
    private RequestSpecification specification;

    public BaseClient(RequestSpecification specification) {
        this.specification = specification;
    }
}
