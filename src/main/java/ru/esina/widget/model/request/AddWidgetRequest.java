package ru.esina.widget.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddWidgetRequest {
    @NotNull
    private Double coordinateX;

    @NotNull
    private Double coordinateY;

    private Double coordinateZ;

    @NotNull
    @Min (1)
    private Double width;

    @NotNull
    @Min (1)
    private Double height;
}
