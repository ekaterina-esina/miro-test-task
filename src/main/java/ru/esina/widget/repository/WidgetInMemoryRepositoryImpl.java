package ru.esina.widget.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import ru.esina.widget.exception.WidgetErrorEnum;
import ru.esina.widget.exception.WidgetException;
import ru.esina.widget.model.Widget;

@Repository
public class WidgetInMemoryRepositoryImpl implements WidgetRepository {
    private final Map<UUID, Widget> map = new ConcurrentHashMap<>();

    @Override
    public void saveWidget(Widget widget) {
	if (!map.computeIfAbsent(widget.getId(), k -> widget).equals(widget)) {
	    throw new WidgetException(WidgetErrorEnum.ERROR_CREATE_WIDGET);
	}
	increaseZ(
	    map.get(widget.getId()).getCoordinateZ(),
	    map.get(widget.getId()).getId());
    }

    @Override
    public void modifyWidget(Widget oldWidget, Widget updatedWidget) {
	if (!map.replace(updatedWidget.getId(), oldWidget, updatedWidget)) {
	    throw new WidgetException(WidgetErrorEnum.ERROR_UPDATE_WIDGET);
	}
	increaseZ(
	    map.get(updatedWidget.getId()).getCoordinateZ(),
	    map.get(updatedWidget.getId()).getId());
    }

    @Override
    public void deleteWidget(UUID uuid) {
	map.entrySet().removeIf(e -> e.getValue().getId().equals(uuid));
    }

    @Override
    public Widget getWidgetByUUID(UUID uuid) {
	return map.get(uuid);
    }

    @Override
    public List<Widget> getAllWidget() {
	return new ArrayList<>(map.values());
    }

    @Override
    public Long generateZCoordinate() {
	if (map.isEmpty()) {
	    return Long.MIN_VALUE;
	}
	return map
	    .entrySet()
	    .stream()
	    .max(Comparator.comparingDouble(s -> s.getValue().getCoordinateZ()))
	    .get()
	    .getValue()
	    .getCoordinateZ() + 1;

    }

    private void increaseZ(Long coordinateZ, UUID id) {
	map.replaceAll(
	    (key, value) -> {
		if (value.getCoordinateZ() >= coordinateZ && key != id) {
		    return value.setCoordinateZ(value.getCoordinateZ() + 1);
		}
		return value;
	    });
    }
}
