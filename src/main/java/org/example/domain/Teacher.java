package org.example.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Teacher {

    @NotBlank
    private String name;

    @NotBlank
    private String subject;

    @NotBlank
    private String registrationNumber;
}
