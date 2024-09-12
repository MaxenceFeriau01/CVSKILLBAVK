package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCvSkillDto {
    private Long id;
    private String civility;
    private String firstName;
    private String name;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private String diploma;

}