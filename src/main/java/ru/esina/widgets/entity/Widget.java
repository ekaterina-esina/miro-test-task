package ru.esina.widgets.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class Widget {
    private UUID id;

    private long coordinateX;

    private long coordinateY;

    private long coordinateZ;

    private double width;

    private double height;

    private ZonedDateTime lastChangesDate;

    public UUID getId() {
        return id;
    }

    public Widget setId(UUID id) {
        this.id = id;
        return this;
    }

    public long getCoordinateX() {
        return coordinateX;
    }

    public Widget setCoordinateX(long coordinateX) {
        this.coordinateX = coordinateX;
        return this;
    }

    public long getCoordinateY() {
        return coordinateY;
    }

    public Widget setCoordinateY(long coordinateY) {
        this.coordinateY = coordinateY;
        return this;
    }

    public long getCoordinateZ() {
        return coordinateZ;
    }

    public Widget setCoordinateZ(long coordinateZ) {
        this.coordinateZ = coordinateZ;
        return this;
    }

    public double getWidth() {
        return width;
    }

    public Widget setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public Widget setHeight(double height) {
        this.height = height;
        return this;
    }

    public ZonedDateTime getLastChangesDate() {
        return lastChangesDate;
    }

    public Widget setLastChangesDate(ZonedDateTime lastChangesDate) {
        this.lastChangesDate = lastChangesDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Widget widget = (Widget) o;
        return coordinateX == widget.coordinateX &&
                coordinateY == widget.coordinateY &&
                coordinateZ == widget.coordinateZ &&
                Double.compare(widget.width, width) == 0 &&
                Double.compare(widget.height, height) == 0 &&
                id.equals(widget.id) &&
                lastChangesDate.equals(widget.lastChangesDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinateX, coordinateY, coordinateZ, width, height, lastChangesDate);
    }

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", coordinateZ=" + coordinateZ +
                ", width=" + width +
                ", height=" + height +
                ", lastChangesDate=" + lastChangesDate +
                '}';
    }
}
