package com.modsen.app.service.impl;


import com.modsen.app.entity.dto.EventDTO;
import org.springframework.http.ResponseEntity;

public interface EventService {


    ResponseEntity<?> findAll(String[] sort);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?>  save(EventDTO eventDTO);


    ResponseEntity<?> update(Long id, EventDTO eventDTO);

    ResponseEntity<String> delete(Long id);


}
