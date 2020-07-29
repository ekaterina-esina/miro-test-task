package ru.esina.widgets.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.esina.widgets.entity.Widget;
import ru.esina.widgets.entity.request.CreateWidgetRequest;
import ru.esina.widgets.entity.request.ModifyWidgetRequest;
import ru.esina.widgets.service.WidgetService;

@RestController
@RequestMapping(value = WidgetController.ENDPOINT_NAME)
public class WidgetController {
    public static final String ENDPOINT_NAME = "/api/widgets";
    private final WidgetService service;

    public WidgetController(WidgetService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Widget create(@RequestBody @Valid CreateWidgetRequest request) {
        return service.create(request);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Widget modify(@RequestBody @Valid ModifyWidgetRequest request) {
        return service.modify(request);
    }

    @DeleteMapping(value = "/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID uuid) {
        service.delete(uuid);
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Widget get(@PathVariable UUID uuid) {
        return service.get(uuid);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Widget> getAll() {
        return service.getAll();
    }
}
