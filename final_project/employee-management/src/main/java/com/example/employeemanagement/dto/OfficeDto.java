package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Office;

/**
 * DTO for {@link Office}
 */
public record OfficeDto(long id, String address, int capacity, String description) {

    public static OfficeDto fromEntity(Office office) {
        if (office == null) {
            return null;
        }
        return new OfficeDto(office.getId(), office.getAddress(), office.getCapacity(), office.getDescription());
    }

}
