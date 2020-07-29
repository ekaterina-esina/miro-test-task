package ru.esina.widgets.entity.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateWidgetRequest {

    @NotNull
    private Long coordinateX;

    @NotNull
    private Long coordinateY;

    private Long coordinateZ;

    @Positive
    @NotNull
    private Double width;

    @Positive
    @NotNull
    private Double height;

    public CreateWidgetRequest(@NotNull Long coordinateX, @NotNull Long coordinateY, Long coordinateZ,
            @Positive @NotNull Double width, @Positive @NotNull Double height) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
        this.width = width;
        this.height = height;
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
        return "CreateWidgetRequest{" +
                "coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", coordinateZ=" + coordinateZ +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
