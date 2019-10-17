package ru.esina.widget.exception;

import lombok.Getter;

public class WidgetException extends RuntimeException {
    @Getter
    private final WidgetErrorEnum error;

    @Getter
    private final String details;

    public WidgetException(WidgetErrorEnum error) {
	this.error = error;
	this.details = null;
    }

    public WidgetException(WidgetErrorEnum error, String details) {
	this.error = error;
	this.details = details;
    }
}
