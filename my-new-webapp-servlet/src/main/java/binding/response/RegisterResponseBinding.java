package binding.response;

public class RegisterResponseBinding implements ResponseBinding {
    private String message;

    public RegisterResponseBinding(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
