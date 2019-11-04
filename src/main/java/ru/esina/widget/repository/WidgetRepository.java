package ru.esina.widget.repository;

import java.util.List;
import java.util.UUID;

import ru.esina.widget.model.Widget;

public interface WidgetRepository {
    void saveWidget(Widget widget);

    void modifyWidget(Widget updatedWidget);

    void deleteWidget(UUID uuid);

    Widget getWidgetByUUID(UUID uuid);

    List<Widget> getAllWidget();

    Long generateZCoordinate();
}
