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

    private Long coordinateZ;

    @NotNull
    @Min (1)
    private Double width;

    @NotNull
    @Min (1)
    private Double height;

    /*use for tests*/
    public AddWidgetRequest(@NotNull Double coordinateX, @NotNull Double coordinateY, Long coordinateZ,
	@NotNull @Min (1) Double width, @NotNull @Min (1) Double height) {
	this.coordinateX = coordinateX;
	this.coordinateY = coordinateY;
	this.coordinateZ = coordinateZ;
	this.width = width;
	this.height = height;
    }
}
