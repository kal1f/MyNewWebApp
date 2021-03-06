package binding.response;

import java.util.Objects;

public class ErrorResponseBinding implements ResponseBinding {

    private int code;
    private String message;

    public static final ErrorResponseBinding ERROR_RESPONSE_500 =
            new ErrorResponseBinding(500, "Internal Server Error");

    public static final ErrorResponseBinding ERROR_RESPONSE_404 =
            new ErrorResponseBinding(404, "Not found");

    public static final ErrorResponseBinding ERROR_RESPONSE_422 =
            new ErrorResponseBinding(422, "Unprocessable Entity");

    public static final ErrorResponseBinding ERROR_RESPONSE_403 =
            new ErrorResponseBinding(403, "Insufficient privileges");

    public ErrorResponseBinding(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponseBinding that = (ErrorResponseBinding) o;
        return code == that.code &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }
}
