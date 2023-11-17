package com.endava.spring_data_jpa.rest;

import com.endava.spring_data_jpa.model.InstructorDTO;
import com.endava.spring_data_jpa.service.InstructorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/instructors", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstructorResource {

    private final InstructorService instructorService;

    public InstructorResource(final InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        return ResponseEntity.ok(instructorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructor(@PathVariable final Long id) {
        return ResponseEntity.ok(instructorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createInstructor(
            @RequestBody @Valid final InstructorDTO instructorDTO) {
        final Long createdId = instructorService.create(instructorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateInstructor(@PathVariable final Long id,
            @RequestBody @Valid final InstructorDTO instructorDTO) {
        instructorService.update(id, instructorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable final Long id) {
        instructorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
