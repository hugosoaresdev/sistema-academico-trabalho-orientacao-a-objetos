package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
////
/// ////
/// ///
///
///
///

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "registrationNumber")
public class Student {
    private String registrationNumber;
    private String name;
}
