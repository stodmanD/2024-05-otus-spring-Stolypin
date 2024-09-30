package com.example.hw09mvcview.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import com.example.hw09mvcview.validation.NonNullElements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDto {
    @Min(value = 1, message = "Book id should have positive value")
    private long id;

    @NotBlank(message = "Title should not be blank")
    @Size(min = 2, max = 20, message = "Title should have expected size")
    private String title;

    @NotNull(message = "Author should not be null")
    private Long author;

    @NotEmpty(message = "Genres should not be empty")
    @NonNullElements
    private Set<Long> genres;
}
