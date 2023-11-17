package com.endava.spring_data_jpa.repos;

import com.endava.spring_data_jpa.domain.Course;
import com.endava.spring_data_jpa.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findFirstByInstructor(Instructor instructor);

}
