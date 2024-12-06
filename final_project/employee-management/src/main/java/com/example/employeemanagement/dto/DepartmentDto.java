package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Department;

/**
 * DTO for {@link Department}
 */
public record DepartmentDto(String code, String name, String description, long managerId) {

    public static DepartmentDto fromEntity(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDto(
                department.getCode(),
                department.getName(),
                department.getDescription(),
                department.getManagerId());
    }

}
