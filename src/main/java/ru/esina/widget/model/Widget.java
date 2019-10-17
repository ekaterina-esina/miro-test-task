package ru.esina.widget.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors (chain = true)
public class Widget {
    private UUID id;

    private double coordinateX;

    private double coordinateY;

    private long coordinateZ;

    private double width;

    private double height;

    private ZonedDateTime lastChangesDate;
}
