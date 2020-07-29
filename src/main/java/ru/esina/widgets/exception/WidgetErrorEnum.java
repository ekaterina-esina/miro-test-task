package ru.esina.widgets.exception;

public enum WidgetErrorEnum {
    INVALID_REQUEST(400, "Invalid request"),
    WIDGET_NOT_FOUND(401, "Widget does not exist"),
    UNEXPECTED_ERROR(999, "Unexpected error");

    private final int code;

    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    WidgetErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "WidgetErrorEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
