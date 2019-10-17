package ru.esina.widget.repository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import ru.esina.widget.model.Widget;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class WidgetRepositoryTest {
    @InjectMocks
    private WidgetRepository repository = new WidgetInMemoryRepositoryImpl();

    @Test
    public void testGenerateZSuccessMapEmpty() {
	assertEquals(Optional.of(Long.MIN_VALUE), Optional.of(repository.generateZCoordinate()));
    }

    @Test
    public void testGenerateZSuccess() {
	repository.saveWidget(
	    new Widget()
		.setId(UUID.fromString("ebf4c839-d4a9-4f23-92be-9495af769869"))
		.setCoordinateX(1d)
		.setCoordinateY(1d)
		.setCoordinateZ(1L)
		.setWidth(1d)
		.setHeight(1d)
		.setLastChangesDate(ZonedDateTime.now()));
	assertEquals(Optional.of(2L), Optional.of(repository.generateZCoordinate()));
    }

    @Test
    public void testIncreaseZSuccess() {
	repository.saveWidget(
	    new Widget()
		.setId(UUID.fromString("ebf4c839-d4a9-4f23-92be-9495af769869"))
		.setCoordinateX(1d)
		.setCoordinateY(1d)
		.setCoordinateZ(1L)
		.setWidth(1d)
		.setHeight(1d)
		.setLastChangesDate(ZonedDateTime.now()));
	assertEquals(Optional.of(2L), Optional.of(repository.generateZCoordinate()));
    }
}
