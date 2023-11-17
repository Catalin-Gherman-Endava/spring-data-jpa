package com.endava.spring_data_jpa.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
public class InstructorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String experience;

}
