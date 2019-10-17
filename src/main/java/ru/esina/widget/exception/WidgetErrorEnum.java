package ru.esina.widget.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum WidgetErrorEnum {
    INVALID_REQUEST(400, "Invalid request"),
    WIDGET_NOT_FOUND(401, "Widget not found"),
    WIDGET_LIST_EMPTY(402, "Widget list is empty"),
    UUID_DUPLICATE(500, "Duplicate uuid was generated"),
    ERROR_DELETE_WIDGET(501, "Failed to remove widget. Try again"),
    ERROR_UPDATE_WIDGET(502, "Failed to update widget. Try again"),
    UNEXPECTED_ERROR(999, "Unexpected error");

    @Getter
    private final int code;

    @Getter
    private final String message;
}
