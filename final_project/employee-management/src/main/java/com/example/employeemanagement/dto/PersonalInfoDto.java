package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.PersonalInfo;

import java.time.LocalDate;

/**
 * DTO for {@link PersonalInfo}
 */
public record PersonalInfoDto(long id, String fullName, LocalDate birthdate, LocalDate employmentDate, boolean isMan) {

    public static PersonalInfoDto fromEntity(PersonalInfo personalInfo) {
        if (personalInfo == null) {
            return null;
        }

        return new PersonalInfoDto(
                personalInfo.getId(),
                personalInfo.getFullName(),
                personalInfo.getBirthdate(),
                personalInfo.getEmploymentDate(),
                personalInfo.isMan()
        );
    }

}
