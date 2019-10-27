package ru.esina.widget.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.esina.widget.exception.WidgetErrorEnum;
import ru.esina.widget.exception.WidgetException;
import ru.esina.widget.model.Widget;

@Repository
public class WidgetInMemoryRepositoryImpl implements WidgetRepository {
    private final Map<UUID, Widget> map = new ConcurrentHashMap<>();
    private AtomicLong maxCoordinateZ = new AtomicLong();

    @Override
    public void saveWidget(Widget widget) {
	if (!map.computeIfAbsent(widget.getId(), k -> widget).equals(widget)) {
	    throw new WidgetException(WidgetErrorEnum.ERROR_CREATE_WIDGET);
	}
	increaseZ(map.get(widget.getId()).getCoordinateZ());
    }

    @Override
    public void modifyWidget(Widget oldWidget, Widget updatedWidget) {
	if (!map.replace(updatedWidget.getId(), oldWidget, updatedWidget)) {
	    throw new WidgetException(WidgetErrorEnum.ERROR_UPDATE_WIDGET);
	}
	increaseZ(map.get(updatedWidget.getId()).getCoordinateZ());
    }

    @Override
    public void deleteWidget(UUID uuid) {
	map.remove(uuid);
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
	return maxCoordinateZ.getAndIncrement();
    }

    private void increaseZ(Long coordinateZ) {
	maxCoordinateZ.getAndUpdate(z -> coordinateZ > z ? coordinateZ : z);
    }
}
