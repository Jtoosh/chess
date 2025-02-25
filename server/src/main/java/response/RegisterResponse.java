package response;

public record RegisterResponse(int Statuscode, String username, String authToken, String errMsg) {
}
