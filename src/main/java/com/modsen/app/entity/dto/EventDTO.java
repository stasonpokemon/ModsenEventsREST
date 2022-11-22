package com.modsen.app.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
