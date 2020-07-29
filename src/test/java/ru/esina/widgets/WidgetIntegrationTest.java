package ru.esina.widgets;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.esina.widgets.entity.Widget;
import ru.esina.widgets.entity.request.CreateWidgetRequest;
import ru.esina.widgets.entity.request.ModifyWidgetRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class WidgetIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateWidgetAllParamsNull() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(null, null, null, null, null);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWidgetInvalidWidth() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, -10d, 10d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWidgetInvalidHeight() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 10d, -10d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWidget() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));
    }

    @Test
    public void testCreateWidgetWithoutZ() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, null, 1d, 1d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(0L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));
    }

    @Test
    public void testCreateWidgetsWithDuplicateZ() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/widgets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].coordinateZ").value(1L))
                .andExpect(jsonPath("$[1].coordinateZ").value(2L));
    }

    @Test
    public void testCreateWidgetsWithoutShiftZ() throws Exception {
        CreateWidgetRequest request1 = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        CreateWidgetRequest request2 = new CreateWidgetRequest(1L, 1L, 2L, 1d, 1d);

        String json1 = objectMapper.writeValueAsString(request1);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));

        String json2 = objectMapper.writeValueAsString(request2);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(2L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/widgets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].coordinateZ").value(1L))
                .andExpect(jsonPath("$[1].coordinateZ").value(2L));
    }

    @Test
    public void testModifyWidgetInvalidWidth() throws Exception {
        ModifyWidgetRequest request = new ModifyWidgetRequest(UUID.randomUUID(), 1L, 1L, 1L, 10d, -10d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyWidgetInvalidHeight() throws Exception {
        ModifyWidgetRequest request = new ModifyWidgetRequest(UUID.randomUUID(), 1L, 1L, 1L, 10d, -10d);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyWidget() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        String json = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Widget widget = objectMapper.readValue(content, Widget.class);
        UUID id = widget.getId();

        ModifyWidgetRequest modifyRequest = new ModifyWidgetRequest(id, 2L, 2L, 2L, 2d, 2d);
        String modifyJson = objectMapper.writeValueAsString(modifyRequest);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(modifyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinateX").value(2L))
                .andExpect(jsonPath("$.coordinateY").value(2L))
                .andExpect(jsonPath("$.coordinateZ").value(2L))
                .andExpect(jsonPath("$.width").value(2d))
                .andExpect(jsonPath("$.height").value(2d));
    }

    @Test
    public void testModifyWidgetWhichNotExist() throws Exception {
        ModifyWidgetRequest modifyRequest = new ModifyWidgetRequest(UUID.randomUUID(), 2L, 2L, 2L, 2d, 2d);
        String modifyJson = objectMapper.writeValueAsString(modifyRequest);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(modifyJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Widget does not exist"));
    }

    @Test
    public void testDeleteWidget() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        String json = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Widget widget = objectMapper.readValue(content, Widget.class);
        String id = String.valueOf(widget.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/widgets/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetWidget() throws Exception {
        CreateWidgetRequest request = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        String json = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Widget widget = objectMapper.readValue(content, Widget.class);
        String id = String.valueOf(widget.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/widgets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d));
    }

    @Test
    public void testGetWidgetWhichNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/widgets/" + UUID.randomUUID()))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Widget does not exist"));
    }

    @Test
    public void testGetAllWidgetsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/widgets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Matchers.empty()));
    }

    @Test
    public void testGetAllWidgets() throws Exception {
        CreateWidgetRequest request1 = new CreateWidgetRequest(1L, 1L, 1L, 1d, 1d);
        CreateWidgetRequest request2 = new CreateWidgetRequest(1L, 1L, 2L, 1d, 1d);
        String json1 = objectMapper.writeValueAsString(request1);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(1L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d))
                .andReturn();
        String json2 = objectMapper.writeValueAsString(request2);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/widgets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coordinateX").value(1L))
                .andExpect(jsonPath("$.coordinateY").value(1L))
                .andExpect(jsonPath("$.coordinateZ").value(2L))
                .andExpect(jsonPath("$.width").value(1d))
                .andExpect(jsonPath("$.height").value(1d))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/widgets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].coordinateX").value(1L))
                .andExpect(jsonPath("$.[0].coordinateY").value(1L))
                .andExpect(jsonPath("$.[0].coordinateZ").value(1L))
                .andExpect(jsonPath("$.[0].width").value(1d))
                .andExpect(jsonPath("$.[0].height").value(1d))
                .andExpect(jsonPath("$.[1].coordinateX").value(1L))
                .andExpect(jsonPath("$.[1].coordinateY").value(1L))
                .andExpect(jsonPath("$.[1].coordinateZ").value(2L))
                .andExpect(jsonPath("$.[1].width").value(1d))
                .andExpect(jsonPath("$.[1].height").value(1d))
                .andReturn();
    }
}

