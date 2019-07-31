package dev.chuby.ke_backend_java.error;

/**
 * @author Victor Hugo Olvera Cruz
 * @subject
 */
public class CustomError {

    private String error;
    private String message;

    public CustomError(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
