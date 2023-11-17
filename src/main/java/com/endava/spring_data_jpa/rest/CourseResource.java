package com.endava.spring_data_jpa.rest;

import com.endava.spring_data_jpa.model.CourseDTO;
import com.endava.spring_data_jpa.service.CourseService;
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
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseResource {

    private final CourseService courseService;

    public CourseResource(final CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable final Long id) {
        return ResponseEntity.ok(courseService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCourse(@RequestBody @Valid final CourseDTO courseDTO) {
        final Long createdId = courseService.create(courseDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCourse(@PathVariable final Long id,
            @RequestBody @Valid final CourseDTO courseDTO) {
        courseService.update(id, courseDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable final Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
