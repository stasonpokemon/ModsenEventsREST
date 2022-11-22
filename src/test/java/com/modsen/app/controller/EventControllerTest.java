package com.modsen.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.app.entity.Event;
import com.modsen.app.entity.dto.EventDTO;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.service.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-events-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-events-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EventControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void getAllEvents() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(eventService.findAll(null).getBody())));
    }

    @Test
    void getAllEventsWithSorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events").queryParam("sort", "topic,asc");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(eventService.findAll(new String[]{"topic", "asc"}).getBody())));
    }

    @Test
    void getAllEventsWithSortingAndSortParametersNotValidException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events").queryParam("sort", "topic,descsdsf");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getEventWithStatusOk() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events/{id}", 1);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(eventService.findById(1L).getBody())));
    }

    @Test
    void getEventWithStatusNotFoundAndEventNotFoundException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events/{id}", 4);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EventNotFoundException.class));
    }

    @Test
    void getEventWithStatusBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events/{id}", "4asd0");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewEventWithStatusOk() throws Exception {
        EventDTO eventDTO = modelMapper.map(new Event("Test topic", "Test description", "Test organizer", new Timestamp(new Date().getTime()) , "Test location"), EventDTO.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/events")
                .content(objectMapper.writeValueAsString(eventDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventDTO)));
    }

    @Test
    void updateEventWithStatusResetContent() throws Exception {
        EventDTO eventDTO = modelMapper.map(new Event("Test topic changed", "Test description changed", "Test organizer changed",  new Timestamp(new Date().getTime()), "Test location changed"), EventDTO.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/events/{id}", 1)
                .content(objectMapper.writeValueAsString(eventDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isResetContent())
                .andExpect(content().json(objectMapper.writeValueAsString(eventDTO)));

    }

    @Test
    void updateEventWithStatusNotFoundAndEventNotFoundException() throws Exception {
        Event event = new Event("Test topic changed", "Test description changed", "Test organizer changed",  new Timestamp(new Date().getTime()), "Test location changed");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/events/{id}", 4)
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EventNotFoundException.class));
    }

    @Test
    void updateEventWithStatusBadRequest() throws Exception {
        Event event = new Event("Test topic changed", "Test description changed", "Test organizer changed",  new Timestamp(new Date().getTime()), "Test location changed");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/events/{id}", "2d")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEventWithStatusOk() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/events/{id}", 1);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Event 1 was deleted"));
    }

    @Test
    void deleteEventWithStatusOkNotFoundAndEventNotFoundException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/events/{id}", 4);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void deleteEventWithStatusBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/events/{id}", "1w");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
