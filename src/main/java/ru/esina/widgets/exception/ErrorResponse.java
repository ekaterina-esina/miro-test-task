package ru.esina.widgets.exception;

public class ErrorResponse {
    private int code;
    private String message;
    private String details;

    public ErrorResponse(WidgetErrorEnum e, String details) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.details = details;
    }

    public ErrorResponse(WidgetErrorEnum e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.details = null;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
