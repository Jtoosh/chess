package response;

public record LoginResponse(int statusCode, String username, String authToken, String errMsg) {
}
