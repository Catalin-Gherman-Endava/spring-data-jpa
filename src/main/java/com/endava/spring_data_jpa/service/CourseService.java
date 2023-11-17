package com.endava.spring_data_jpa.service;

import com.endava.spring_data_jpa.domain.Course;
import com.endava.spring_data_jpa.domain.Instructor;
import com.endava.spring_data_jpa.domain.Student;
import com.endava.spring_data_jpa.model.CourseDTO;
import com.endava.spring_data_jpa.repos.CourseRepository;
import com.endava.spring_data_jpa.repos.InstructorRepository;
import com.endava.spring_data_jpa.repos.StudentRepository;
import com.endava.spring_data_jpa.util.NotFoundException;
import com.endava.spring_data_jpa.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    public CourseService(final CourseRepository courseRepository,
            final InstructorRepository instructorRepository,
            final StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
    }

    public List<CourseDTO> findAll() {
        final List<Course> courses = courseRepository.findAll(Sort.by("id"));
        return courses.stream()
                .map(course -> mapToDTO(course, new CourseDTO()))
                .toList();
    }

    public CourseDTO get(final Long id) {
        return courseRepository.findById(id)
                .map(course -> mapToDTO(course, new CourseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CourseDTO courseDTO) {
        final Course course = new Course();
        mapToEntity(courseDTO, course);
        return courseRepository.save(course).getId();
    }

    public void update(final Long id, final CourseDTO courseDTO) {
        final Course course = courseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(courseDTO, course);
        courseRepository.save(course);
    }

    public void delete(final Long id) {
        final Course course = courseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName").ascending());
        studentRepository.findAllByCourses(course, pageable)
                .forEach(student -> student.getCourses().remove(course));
        courseRepository.delete(course);
    }

    private CourseDTO mapToDTO(final Course course, final CourseDTO courseDTO) {
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setInstructor(course.getInstructor() == null ? null : course.getInstructor().getId());
        return courseDTO;
    }

    private Course mapToEntity(final CourseDTO courseDTO, final Course course) {
        course.setName(courseDTO.getName());
        final Instructor instructor = courseDTO.getInstructor() == null ? null : instructorRepository.findById(courseDTO.getInstructor())
                .orElseThrow(() -> new NotFoundException("instructor not found"));
        course.setInstructor(instructor);
        return course;
    }

    public String getReferencedWarning(final Long id) {
        final Course course = courseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Student coursesStudent = studentRepository.findFirstByCourses(course);
        if (coursesStudent != null) {
            return WebUtils.getMessage("course.student.courses.referenced", coursesStudent.getId());
        }
        return null;
    }

}
