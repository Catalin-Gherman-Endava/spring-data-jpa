package com.endava.spring_data_jpa.service;

import com.endava.spring_data_jpa.domain.Course;
import com.endava.spring_data_jpa.domain.Instructor;
import com.endava.spring_data_jpa.model.InstructorDTO;
import com.endava.spring_data_jpa.repos.CourseRepository;
import com.endava.spring_data_jpa.repos.InstructorRepository;
import com.endava.spring_data_jpa.util.NotFoundException;
import com.endava.spring_data_jpa.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    public InstructorService(final InstructorRepository instructorRepository,
            final CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }

    public List<InstructorDTO> findAll() {
        final List<Instructor> instructors = instructorRepository.findAll(Sort.by("id"));
        return instructors.stream()
                .map(instructor -> mapToDTO(instructor, new InstructorDTO()))
                .toList();
    }

    public InstructorDTO get(final Long id) {
        return instructorRepository.findById(id)
                .map(instructor -> mapToDTO(instructor, new InstructorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InstructorDTO instructorDTO) {
        final Instructor instructor = new Instructor();
        mapToEntity(instructorDTO, instructor);
        return instructorRepository.save(instructor).getId();
    }

    public void update(final Long id, final InstructorDTO instructorDTO) {
        final Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(instructorDTO, instructor);
        instructorRepository.save(instructor);
    }

    public void delete(final Long id) {
        instructorRepository.deleteById(id);
    }

    private InstructorDTO mapToDTO(final Instructor instructor, final InstructorDTO instructorDTO) {
        instructorDTO.setId(instructor.getId());
        instructorDTO.setName(instructor.getName());
        instructorDTO.setExperience(instructor.getExperience());
        return instructorDTO;
    }

    private Instructor mapToEntity(final InstructorDTO instructorDTO, final Instructor instructor) {
        instructor.setName(instructorDTO.getName());
        instructor.setExperience(instructorDTO.getExperience());
        return instructor;
    }

    public String getReferencedWarning(final Long id) {
        final Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Course instructorCourse = courseRepository.findFirstByInstructor(instructor);
        if (instructorCourse != null) {
            return WebUtils.getMessage("instructor.course.instructor.referenced", instructorCourse.getId());
        }
        return null;
    }

}
