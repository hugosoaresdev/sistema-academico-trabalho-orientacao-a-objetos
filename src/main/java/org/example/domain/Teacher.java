package org.example.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "registrationNumber")

public class Teacher {

    private String name;
    private String subject;
    private String registrationNumber;

}
