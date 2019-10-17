package ru.esina.widget.controller;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.esina.widget.model.Widget;
import ru.esina.widget.model.request.AddWidgetRequest;
import ru.esina.widget.model.request.EditWidgetRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith (SpringRunner.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class WidgetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID id;


    @Test
    public void testCreateWidgetAllParamsNull() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(null, null, null, null, null);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWidgetInvalidWidth() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(-1d, -1d, 1L, -10d, 10d);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWidgetInvalidHeight() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(1d, 1d, -1L, 10d, -10d);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWidgetSuccess() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(1d, 1d, 1L, 1d, 1d);
	String json = objectMapper.writeValueAsString(request);
	MvcResult result = mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isCreated())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateX").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateY").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateZ").value(1L))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.width").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(1d))
	    .andReturn();
	String content = result.getResponse().getContentAsString();
	Widget widget = objectMapper.readValue(content, Widget.class);
	id = widget.getId();
    }

    @Test
    public void testCreateWidgetSuccessDublicateZ() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(1d, 1d, 1L, 1d, 1d);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isCreated())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateX").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateY").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateZ").value(1L))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.width").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(1d));

    }

    @Test
    public void testCreateWidgetSuccessWithoutZ() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(1d, 1d, null, 1d, 1d);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isCreated())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateX").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateY").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.width").value(1d))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(1d));
    }

    @Test
    public void testCreateWidgetInvalidJson() throws Exception {
	AddWidgetRequest request = new AddWidgetRequest(1d, 1d, 1L, 1d, 1d);
	mockMvc.perform(MockMvcRequestBuilders
	    .post("/widget/create")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(request.toString()))
	    .andExpect(status().isBadRequest());
    }


    @Test
    public void testModifyWidgetInvalidWidth() throws Exception {
	EditWidgetRequest request = new EditWidgetRequest(
	    UUID.fromString("ebf4c839-d4a9-4f23-92be-9495af769869"),
	    null, null, null, -5d, null);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .put("/widget/modify")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyWidgetInvalidHeight() throws Exception {
	EditWidgetRequest request = new EditWidgetRequest(
	    UUID.fromString("ebf4c839-d4a9-4f23-92be-9495af769869"),
	    null, null, null, null, -5d);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .put("/widget/modify")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyWidgetSuccess() throws Exception {
	EditWidgetRequest request = new EditWidgetRequest(id, null, null, 2L, 5d, null);
	String json = objectMapper.writeValueAsString(request);
	mockMvc.perform(MockMvcRequestBuilders
	    .put("/widget/modify")
	    .contentType(APPLICATION_JSON_UTF8)
	    .content(json))
	    .andExpect(status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.coordinateZ").value(2L))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.width").value(5d));
    }

    @Test
    public void testGetWidgetSuccess() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders
	    .get("/widget/get/" + id.toString()))
	    .andExpect(status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }

    @Test
    public void testGetAllWidgetSuccess() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders
	    .get("/widget/getAll"))
	    .andExpect(status().isOk())
	    .andExpect(MockMvcResultMatchers.jsonPath("$[0].coordinateZ").value(Long.MIN_VALUE))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[1].coordinateZ").value(1L))
	    .andExpect(MockMvcResultMatchers.jsonPath("$[2].coordinateZ").value(2L));

    }

    @Test
    public void testDeleteWidget() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders
	    .delete("/widget/delete/ebf4c839-d4a9-4f23-92be-9495af769869"))
	    .andExpect(status().isNoContent());
    }
}

