package com.endava.spring_data_jpa.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    private Integer age;

    private List<Long> courses;

}
