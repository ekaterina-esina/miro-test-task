package ru.esina.widget.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    //todo проверить float


    @RequestMapping (
	value = "/create",
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus (HttpStatus.CREATED)
    public Widget createWidget(@RequestBody @Valid AddWidgetRequest request) {
	return service.createWidget(
	    request.getCoordinateX(),
	    request.getCoordinateY(),
	    request.getCoordinateZ(),
	    request.getWidth(),
	    request.getHeight()
	);
    }

    @RequestMapping (
	value = "/modify",
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus (HttpStatus.OK)
    public Widget modifyWidget(@RequestBody @Valid EditWidgetRequest request) {
	return service.modifyWidget(
	    request.getId(),
	    request.getCoordinateX(),
	    request.getCoordinateY(),
	    request.getCoordinateZ(),
	    request.getWidth(),
	    request.getHeight()
	);
    }

    @RequestMapping (
	value = "/delete/{uuid}",
	method = RequestMethod.DELETE)
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteWidget(@PathVariable UUID uuid) {
	service.deleteWidget(uuid);
    }

    @RequestMapping (
	value = "/get/{uuid}",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus (HttpStatus.OK)
    public Widget getWidget(@PathVariable UUID uuid) {
	return service.getWidgetByUUID(uuid);
    }

    @RequestMapping (
	value = "/getAll",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus (HttpStatus.OK)
    public List<Widget> getAllWidget() {
	return service.getAllWidget();
    }
}
