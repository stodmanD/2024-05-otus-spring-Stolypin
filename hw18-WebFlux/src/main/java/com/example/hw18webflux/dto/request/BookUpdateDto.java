package com.example.hw18webflux.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.hw18webflux.validation.NonNullElements;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDto {
    @NotNull(message = "Id should not be null")
    private String id;

    @NotBlank(message = "Title should not be blank")
    @Size(min = 2, max = 20, message = "Title should have expected size")
    private String title;

    @NotNull(message = "Author should not be null")
    private String authorId;

    @NotEmpty(message = "Genres should not be empty")
    @NonNullElements
    private Set<String> genreIds;
}
