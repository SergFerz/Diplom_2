package api;

public enum Api {
    LOGIN ("/api/auth/login"),
    USER_DATA ("/api/auth/user"),
    REGISTER_USER ("/api/auth/register"),
    ORDER_DATA ("/api/orders"),
    INGREDIENT ("/api/ingredients");

    public String path;

    Api(String path) {
        this.path = path;
    }
}
