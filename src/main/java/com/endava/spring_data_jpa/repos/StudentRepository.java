package com.endava.spring_data_jpa.repos;

import com.endava.spring_data_jpa.domain.Course;
import com.endava.spring_data_jpa.domain.Student;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findFirstByCourses(Course course);

    Page<Student> findAllByCourses(Course course, Pageable pageable);

}
