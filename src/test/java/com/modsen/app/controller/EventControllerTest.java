package com.modsen.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.app.entity.dto.EventDTO;
import com.modsen.app.entity.Event;
import com.modsen.app.exception.EventNotFoundException;
import com.modsen.app.service.EventService;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-events-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-events-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EventControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void getAllEvents() throws Exception {
        List<EventDTO> eventsDTO = eventService.findAll().stream().map(event -> modelMapper.map(event, EventDTO.class)).collect(Collectors.toList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(eventsDTO)));
    }

    @Test
    void getEventWithStatusOk() throws Exception {
        EventDTO expectedEventDTO = modelMapper.map(eventService.findById(1L), EventDTO.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/events/{id}", 1);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedEventDTO)));
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.topic").value("Spring REST"))
//                .andExpect(jsonPath("$.description").value("Today we will talk about Spring REST application"))
//                .andExpect(jsonPath("$.organizer").value("MODSEN Group"))
//                .andExpect(jsonPath("$.location").value("Vitebsk"));
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
        Timestamp testCurrentTime = new Timestamp(new Date().getTime());
        EventDTO eventDTO = modelMapper.map(new Event("Test topic", "Test description", "Test organizer", testCurrentTime, "Test location"), EventDTO.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/events")
                .content(objectMapper.writeValueAsString(eventDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("Test topic"))
                .andExpect(jsonPath("$.description").value("Test description"))
                .andExpect(jsonPath("$.organizer").value("Test organizer"))
//                .andExpect(jsonPath("$.eventTime").value(testCurrentTime))
                .andExpect(jsonPath("$.location").value("Test location"));
    }

    @Test
    void updateEventWithStatusResetContent() throws Exception {
        Timestamp testCurrentTime = new Timestamp(new Date().getTime());
        EventDTO eventDTO = modelMapper.map(new Event("Test topic changed", "Test description changed", "Test organizer changed", testCurrentTime, "Test location changed"), EventDTO.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/events/{id}", 1)
                .content(objectMapper.writeValueAsString(eventDTO))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isResetContent())
                .andExpect(jsonPath("$.topic").value("Test topic changed"))
                .andExpect(jsonPath("$.description").value("Test description changed"))
                .andExpect(jsonPath("$.organizer").value("Test organizer changed"))
//                .andExpect(jsonPath("$.eventTime").value(testCurrentTime))
                .andExpect(jsonPath("$.location").value("Test location changed"));
    }

    @Test
    void updateEventWithStatusNotFoundAndEventNotFoundException() throws Exception {
        Timestamp testCurrentTime = new Timestamp(new Date().getTime());
        Event event = new Event("Test topic changed", "Test description changed", "Test organizer changed", testCurrentTime, "Test location changed");

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
        Timestamp testCurrentTime = new Timestamp(new Date().getTime());
        Event event = new Event("Test topic changed", "Test description changed", "Test organizer changed", testCurrentTime, "Test location changed");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/events/{id}", "2d")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEventWithStatusOk() throws Exception {
        Event event = eventService.findById(1L);
        System.out.println(event);
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
                .andExpect(content().contentType("application/json"))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EventNotFoundException.class));
    }

    @Test
    void deleteEventWithStatusBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/events/{id}", "1w");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
