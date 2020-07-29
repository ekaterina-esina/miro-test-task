package ru.esina.widgets.exception;

public class WidgetException extends RuntimeException {
    private final WidgetErrorEnum error;

    private final String details;

    public WidgetException(WidgetErrorEnum error) {
        this.error = error;
        this.details = null;
    }

    public WidgetException(WidgetErrorEnum error, String details) {
        this.error = error;
        this.details = details;
    }

    public WidgetException(WidgetErrorEnum error, Throwable ex) {
        this.error = error;
        this.details = String.format("Cause=%s, message=%s", ex.getCause(), ex.getMessage());
    }

    public WidgetErrorEnum getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "WidgetException{" +
                "error=" + error +
                ", details='" + details + '\'' +
                '}';
    }
}
