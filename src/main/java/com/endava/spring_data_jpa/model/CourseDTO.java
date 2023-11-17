package com.endava.spring_data_jpa.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CourseDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    private Long instructor;

}
