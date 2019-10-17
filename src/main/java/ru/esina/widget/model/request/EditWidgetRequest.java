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

    private Long coordinateZ;

    @Min (1)
    private Double width;

    @Min (1)
    private Double height;

    /*use for tests*/
    public EditWidgetRequest(@NotNull UUID id, Double coordinateX, Double coordinateY, Long coordinateZ, @Min (1) Double width,
	@Min (1) Double height) {
	this.id = id;
	this.coordinateX = coordinateX;
	this.coordinateY = coordinateY;
	this.coordinateZ = coordinateZ;
	this.width = width;
	this.height = height;
    }
}
