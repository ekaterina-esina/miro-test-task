package ru.esina.widgets.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.esina.widgets.entity.request.CreateWidgetRequest;
import ru.esina.widgets.entity.request.ModifyWidgetRequest;
import ru.esina.widgets.entity.Widget;
import ru.esina.widgets.exception.WidgetException;
import ru.esina.widgets.repository.WidgetRepository;

import static ru.esina.widgets.exception.WidgetErrorEnum.UNEXPECTED_ERROR;

@Service
public class WidgetServiceImpl implements WidgetService {
    private final WidgetRepository repository;
    private static Comparator<Widget> zCoordinateComparator = Comparator.comparingLong(Widget::getCoordinateZ);

    public WidgetServiceImpl(WidgetRepository repository) {this.repository = repository;}

    @Override
    public Widget create(CreateWidgetRequest request) {
        Widget widget = new Widget()
                .setId(UUID.randomUUID())
                .setCoordinateX(request.getCoordinateX())
                .setCoordinateY(request.getCoordinateY())
                .setWidth(request.getWidth())
                .setHeight(request.getHeight())
                .setLastChangesDate(ZonedDateTime.now());

        repository.lock();
        try {
            widget.setCoordinateZ(request.getCoordinateZ() == null ? getTopZ() : request.getCoordinateZ());
            var zTail = getAllOrderedByZ().tailMap(widget.getCoordinateZ());
            reorderZ(zTail, widget.getId());

            repository.save(widget);
        } finally {
            repository.unlock();
        }
        return widget;
    }

    @Override
    public Widget modify(ModifyWidgetRequest request) {
        Widget widget = repository.get(request.getId());
        setChangedFields(widget, request);

        repository.lock();
        try {
            var zTail = getAllOrderedByZ().tailMap(widget.getCoordinateZ(), true);
            reorderZ(zTail, widget.getId());
            repository.save(widget);
        } finally {
            repository.unlock();
        }
        return widget;
    }

    @Override
    public void delete(UUID uuid) {
        repository.delete(uuid);
    }

    @Override
    public Widget get(UUID uuid) {
        return repository.get(uuid);
    }

    @Override
    public List<Widget> getAll() {
        return new ArrayList<>(getAllOrderedByZ().values());
    }

    private void reorderZ(SortedMap<Long, Widget> zTail, UUID id) {
        if (zTail.size() == 0) {
            return;
        }
        long z = zTail.firstKey();
        for (var widget : zTail.values()) {
            if (Objects.equals(id, widget.getId())) {
                continue;
            }
            z += 1;
            widget.setCoordinateZ(z);
            widget.setLastChangesDate(ZonedDateTime.now());
            repository.save(widget);
        }
    }

    private long getTopZ() {
        Widget maxZWidget = repository.getAll()
                .values()
                .stream()
                .max(zCoordinateComparator)
                .orElse(null);
        return maxZWidget == null ? 0 : maxZWidget.getCoordinateZ() + 1;
    }

    private TreeMap<Long, Widget> getAllOrderedByZ() {
        return repository.getAll()
                .values()
                .stream()
                .collect(
                        Collectors.toMap(
                                Widget::getCoordinateZ,
                                Function.identity(),
                                (a, b) -> b,
                                TreeMap::new
                        )
                );
    }

    private void setChangedFields(Widget widget, ModifyWidgetRequest request) {
        if (Objects.nonNull(request.getCoordinateX())) {
            widget.setCoordinateX(request.getCoordinateX());
        }
        if (Objects.nonNull(request.getCoordinateY())) {
            widget.setCoordinateY(request.getCoordinateY());
        }
        if (Objects.nonNull(request.getCoordinateZ())) {
            widget.setCoordinateZ(request.getCoordinateZ());
        }
        if (Objects.nonNull(request.getWidth())) {
            widget.setWidth(request.getWidth());
        }
        if (Objects.nonNull(request.getHeight())) {
            widget.setHeight(request.getHeight());
        }

        widget.setLastChangesDate(ZonedDateTime.now());
    }
}
