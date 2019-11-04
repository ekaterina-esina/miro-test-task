package ru.esina.widget.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.esina.widget.model.Widget;
import ru.esina.widget.model.request.AddWidgetRequest;
import ru.esina.widget.model.request.EditWidgetRequest;
import ru.esina.widget.service.WidgetService;

@RestController
@RequestMapping (value = WidgetController.PRIMARY_MAPPING)
public class WidgetController {
    public static final String PRIMARY_MAPPING = "/widget";
    private final WidgetService service;

    public WidgetController(WidgetService service) {
	this.service = service;
    }

    @PostMapping (
	consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Widget> createWidget(@RequestBody @Valid AddWidgetRequest request) {
	return new ResponseEntity<>(service.createWidget(
	    request.getCoordinateX(),
	    request.getCoordinateY(),
	    request.getCoordinateZ(),
	    request.getWidth(),
	    request.getHeight()
	), HttpStatus.CREATED);
    }

    @PutMapping (
	consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Widget> modifyWidget(@RequestBody @Valid EditWidgetRequest request) {
	return new ResponseEntity<>(service.modifyWidget(
	    request.getId(),
	    request.getCoordinateX(),
	    request.getCoordinateY(),
	    request.getCoordinateZ(),
	    request.getWidth(),
	    request.getHeight()
	), HttpStatus.OK);
    }

    @DeleteMapping (value = "/{uuid}")
    public ResponseEntity deleteWidget(@PathVariable UUID uuid) {
	service.deleteWidget(uuid);
	return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping (
	value = "/{uuid}",
	produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Widget> getWidget(@PathVariable UUID uuid) {
	return new ResponseEntity<>(service.getWidgetByUUID(uuid), HttpStatus.OK);
    }

    @GetMapping (
	produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Widget>> getAllWidget() {
	return new ResponseEntity<>(service.getAllWidget(), HttpStatus.OK);
    }
}
