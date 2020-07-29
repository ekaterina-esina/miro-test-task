package ru.esina.widgets.entity.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ModifyWidgetRequest {
    @NotNull
    private UUID id;

    private Long coordinateX;

    private Long coordinateY;

    private Long coordinateZ;

    @Positive
    private Double width;
    @Positive
    private Double height;

    public ModifyWidgetRequest(@NotNull UUID id, Long coordinateX, Long coordinateY, Long coordinateZ, @Positive Double width,
            @Positive Double height) {
        this.id = id;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
        this.width = width;
        this.height = height;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Long coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Long getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Long coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Long getCoordinateZ() {
        return coordinateZ;
    }

    public void setCoordinateZ(Long coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ModifyWidgetRequest{" +
                "id=" + id +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", coordinateZ=" + coordinateZ +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
