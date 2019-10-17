package ru.esina.widget.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors (chain = true)
public class Widget {
    @NotNull
    private UUID id;

    @NotNull
    private Double coordinateX;

    @NotNull
    private Double coordinateY;

    @NotNull
    private Double coordinateZ;

    @NotNull
    @Min (1)
    private Double width;

    @NotNull
    @Min (1)
    private Double height;

    @NotNull
    private ZonedDateTime lastChangesDate;
}
