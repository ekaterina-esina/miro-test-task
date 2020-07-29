package ru.esina.widgets.service;

import java.util.List;
import java.util.UUID;

import ru.esina.widgets.entity.Widget;
import ru.esina.widgets.entity.request.CreateWidgetRequest;
import ru.esina.widgets.entity.request.ModifyWidgetRequest;

public interface WidgetService {
    Widget create(CreateWidgetRequest request);

    Widget modify(ModifyWidgetRequest request);

    void delete(UUID uuid);

    Widget get(UUID uuid);

    List<Widget> getAll();
}
