package com.example.employeemanagement.dto;

import java.time.LocalDate;

public record PersonalInfoUpdateDto(String fullName, LocalDate birthdate, LocalDate employmentDate, boolean isMan) {
}
