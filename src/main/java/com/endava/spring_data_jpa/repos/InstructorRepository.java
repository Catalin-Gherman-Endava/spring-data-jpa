package com.endava.spring_data_jpa.repos;

import java.util.List;

import com.endava.spring_data_jpa.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
