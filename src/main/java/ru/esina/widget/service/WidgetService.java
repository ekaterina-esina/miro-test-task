package ru.esina.widget.service;

import java.util.List;
import java.util.UUID;

import ru.esina.widget.model.Widget;

public interface WidgetService {
    Widget createWidget(Double x, Double y, Long z, Double width, Double height);

    Widget modifyWidget(UUID uuid, Double x, Double y, Long z, Double width, Double height);

    void deleteWidget(UUID uuid);

    Widget getWidgetByUUID(UUID uuid);

    List<Widget> getAllWidget();
}
