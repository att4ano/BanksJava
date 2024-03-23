package application.result;

public enum LoginResult {
    SUCCESS("Success"),
    FAILURE("Failure"),
    NOT_FOUND("Not found");
    private final String message;

    LoginResult(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
