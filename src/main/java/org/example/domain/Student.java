package org.example.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "registrationNumber")

public class Student {

    @NotBlank
    private String registrationNumber;

    @NotBlank
    private String name;
}
