package com.modsen.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topic", nullable = false)
    private String topic;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "organizer", nullable = false)
    private String organizer;
    @Column(name = "event_time", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Timestamp eventTime;
    @Column(name = "location", nullable = false)
    private String location;


}
