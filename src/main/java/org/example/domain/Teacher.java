package org.example.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "registrationNumber")

public class Teacher {

    @NotBlank
    private String name;

    @NotBlank
    private String subject;

    @NotBlank
    private String registrationNumber;
}
