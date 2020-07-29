package ru.esina.widgets.repository;

import org.springframework.stereotype.Repository;
import ru.esina.widgets.entity.Widget;
import ru.esina.widgets.exception.WidgetException;

import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import static ru.esina.widgets.exception.WidgetErrorEnum.UNEXPECTED_ERROR;
import static ru.esina.widgets.exception.WidgetErrorEnum.WIDGET_NOT_FOUND;

@Repository
public class WidgetInMemoryRepositoryImpl implements WidgetRepository {
    private final ReentrantLock lock = new ReentrantLock();
    private final TreeMap<UUID, Widget> widgets = new TreeMap<>();

    @Override
    public void lock() {
        try {
            this.lock.lockInterruptibly();
        } catch (InterruptedException e) {
            throw new WidgetException(UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public void unlock() {
        this.lock.unlock();
    }

    @Override
    public Widget save(Widget widget) {
        widgets.put(widget.getId(), widget);
        return widget;
    }

    @Override
    public void delete(UUID uuid) {
        widgets.remove(uuid);
    }

    @Override
    public Widget get(UUID uuid) {
        Widget widget = widgets.get(uuid);
        if (widget == null) {
            throw new WidgetException(WIDGET_NOT_FOUND, String.format("uuid=%s", uuid));
        }
        return widget;
    }

    @Override
    public TreeMap<UUID, Widget> getAll() {
        return widgets;
    }
}
