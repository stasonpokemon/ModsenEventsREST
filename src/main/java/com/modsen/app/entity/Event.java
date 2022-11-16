package com.modsen.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "topic", nullable = false)
    @NotEmpty(message = "Topic should be not empty")
    private String topic;

    @Column(name = "description", nullable = false)
    @NotEmpty(message = "Description should be not empty")
    private String description;

    @Column(name = "organizer", nullable = false)
    @NotEmpty(message = "Organizer should be not empty")
    private String organizer;

    @Column(name = "event_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    @NotNull(message = "Event time should be not null")
    private Timestamp eventTime;

    @Column(name = "location", nullable = false)
    @NotEmpty(message = "Location should be not empty")
    private String location;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Event(String topic, String description, String organizer, Timestamp eventTime, String location) {
        this.topic = topic;
        this.description = description;
        this.organizer = organizer;
        this.eventTime = eventTime;
        this.location = location;
    }
}
