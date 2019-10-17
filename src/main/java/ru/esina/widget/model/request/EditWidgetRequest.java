package ru.esina.widget.model.request;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EditWidgetRequest {
    @NotNull
    private UUID id;

    private Double coordinateX;

    private Double coordinateY;

    private Double coordinateZ;

    @Min (1)
    private Double width;

    @Min (1)
    private Double height;
}
