package ru.esina.widget.model;

import lombok.Data;
import ru.esina.widget.exception.WidgetErrorEnum;

@Data
public class ErrorResponse {
    private Integer code;
    private String message;
    private String details;

    public ErrorResponse(WidgetErrorEnum e, String details) {
	this.code = e.getCode();
	this.message = e.getMessage();
	this.details = details;
    }
}
