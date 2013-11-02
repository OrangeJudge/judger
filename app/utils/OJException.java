package utils;

public class OJException extends Exception {
    private int code;
    private String message;
    public OJException(int code) {
        this.code = code;
    }
    public OJException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
