package ru.esina.widgets.repository;

import java.util.TreeMap;
import java.util.UUID;

import ru.esina.widgets.entity.Widget;

public interface WidgetRepository {
    void lock();

    void unlock();

    Widget save(Widget widget);

    void delete(UUID uuid);

    Widget get(UUID uuid);

    TreeMap<UUID, Widget> getAll();
}
