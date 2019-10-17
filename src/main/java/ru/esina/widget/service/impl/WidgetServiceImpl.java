package ru.esina.widget.service.impl;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.esina.widget.exception.WidgetErrorEnum;
import ru.esina.widget.exception.WidgetException;
import ru.esina.widget.model.Widget;
import ru.esina.widget.repository.WidgetRepository;
import ru.esina.widget.service.WidgetService;

@Service
public class WidgetServiceImpl implements WidgetService {
    private final WidgetRepository repository;

    public WidgetServiceImpl(WidgetRepository repository) {
	this.repository = repository;
    }

    @Override
    public Widget createWidget(Double x, Double y, Double z, Double width, Double height) {
	final Widget widget = new Widget()
	    .setId(UUID.randomUUID())
	    .setCoordinateX(x)
	    .setCoordinateY(y)
	    .setCoordinateZ(z == null ? repository.generateZCoordinate() : z)
	    .setHeight(height)
	    .setWidth(width)
	    .setLastChangesDate(ZonedDateTime.now());
	repository.saveWidget(widget);
	return widget;
    }

    @Override
    public Widget modifyWidget(UUID uuid, Double x, Double y, Double z, Double width, Double height) {
	final Widget oldWidget = getWidgetByUUID(uuid);
	final Widget updatedWidget = new Widget()
	    .setId(oldWidget.getId())
	    .setCoordinateX(x == null ? oldWidget.getCoordinateX() : x)
	    .setCoordinateY(y == null ? oldWidget.getCoordinateY() : y)
	    .setCoordinateZ(z == null ? oldWidget.getCoordinateZ() : z)
	    .setWidth(width == null ? oldWidget.getWidth() : width)
	    .setHeight(height == null ? oldWidget.getHeight() : height)
	    .setLastChangesDate(ZonedDateTime.now());
	repository.modifyWidget(oldWidget, updatedWidget);
	return updatedWidget;
    }

    @Override
    public void deleteWidget(UUID uuid) {
	repository.deleteWidget(uuid);
    }

    @Override
    public Widget getWidgetByUUID(UUID uuid) {
	Widget widget = repository.getWidgetByUUID(uuid);
	if (widget == null) {
	    throw new WidgetException(WidgetErrorEnum.WIDGET_NOT_FOUND, uuid.toString());
	}
	return widget;
    }

    @Override
    public List<Widget> getAllWidget() {
	List<Widget> widgetList = repository.getAllWidget();
	if (widgetList == null || widgetList.isEmpty()) {
	    throw new WidgetException(WidgetErrorEnum.WIDGET_LIST_EMPTY);
	}
	return widgetList
	    .stream()
	    .sorted(Comparator.comparingDouble(Widget::getCoordinateZ))
	    .collect(Collectors.toList());
    }
}
