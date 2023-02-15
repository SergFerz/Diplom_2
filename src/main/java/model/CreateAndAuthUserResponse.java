package model;

public class CreateAndAuthUserResponse {
    private String accessToken;
    private String refreshToken;
    private boolean success;
    private User user;

    public CreateAndAuthUserResponse(String accessToken, String refreshToken, boolean success, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.success = success;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
