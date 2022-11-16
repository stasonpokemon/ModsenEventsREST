package com.modsen.app.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modsen.app.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {


    @NotEmpty(message = "Topic should be not empty")
    private String topic;

    @NotEmpty(message = "Description should be not empty")
    private String description;

    @NotEmpty(message = "Organizer should be not empty")
    private String organizer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    @NotNull(message = "Event time should be not null")
    private Timestamp eventTime;

    @NotEmpty(message = "Location should be not empty")
    private String location;

//    public static Event toEvent(EventDTO eventDTO) {
//        return new ModelMapper().map(eventDTO, Event.class);
//    }
//
//    public static EventDTO fromEvent(Event event) {
//        return new ModelMapper().map(event, EventDTO.class);
//    }


}
