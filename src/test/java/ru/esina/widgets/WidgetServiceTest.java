package ru.esina.widgets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.esina.widgets.entity.Widget;
import ru.esina.widgets.entity.request.CreateWidgetRequest;
import ru.esina.widgets.entity.request.ModifyWidgetRequest;
import ru.esina.widgets.exception.WidgetException;
import ru.esina.widgets.repository.WidgetInMemoryRepositoryImpl;
import ru.esina.widgets.repository.WidgetRepository;
import ru.esina.widgets.service.WidgetService;
import ru.esina.widgets.service.WidgetServiceImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WidgetServiceTest {
    @TestConfiguration
    static class RectangleWidgetControllerTestContextConfiguration {

        @Bean
        public WidgetService service() {
            return new WidgetServiceImpl(repository());
        }

        @Bean
        public WidgetRepository repository() {
            return new WidgetInMemoryRepositoryImpl();
        }
    }

    @Autowired
    private WidgetServiceImpl service;

    @Test
    public void create() {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        Widget widget = service.create(request);
        assertNotNull(widget);
    }

    @Test
    public void modify() {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        Widget widget = service.create(request);

        ModifyWidgetRequest modifyRequest = new ModifyWidgetRequest(widget.getId(), 1L, 1L, 2L, 1d, 1d);
        Widget modifiedWidget = service.modify(modifyRequest);

        assertEquals(widget.setCoordinateZ(2), modifiedWidget);
    }

    @Test
    public void get() {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        Widget widget = service.create(request);

        Widget widgetFromRepository = service.get(widget.getId());

        assertEquals(widget, widgetFromRepository);
    }

    @Test(expected = WidgetException.class)
    public void delete() {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        Widget widget = service.create(request);

        service.delete(widget.getId());

        service.get(widget.getId());
    }

    @Test
    public void createMultiThreaded() throws InterruptedException {
        var tp = Executors.newFixedThreadPool(4);
        var expectedNum = 1000;
        for (int i = 0; i < expectedNum; i++) {
            tp.submit(() -> {
                var widget = new CreateWidgetRequest(1L, 1L, 1L, 1D, 1D);
                service.create(widget);
            });
        }

        tp.shutdown();
        tp.awaitTermination(2L, TimeUnit.MINUTES);
        assertEquals(expectedNum, service.getAll().size());
    }
}
