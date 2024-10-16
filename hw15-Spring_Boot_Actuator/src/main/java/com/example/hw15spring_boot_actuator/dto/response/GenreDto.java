package com.example.hw15spring_boot_actuator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenreDto {
    private long id;

    private String name;
}
