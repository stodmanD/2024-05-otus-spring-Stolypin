package com.example.hw11spring_security_authentication.dto.request;

import com.example.hw11spring_security_authentication.validation.NonNullElements;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDto {
    @NotNull(message = "Id should not be null")
    private Long id;

    @NotBlank(message = "Title should not be blank")
    @Size(min = 2, max = 20, message = "Title should have expected size")
    private String title;

    @NotNull(message = "Author should not be null")
    private Long authorId;

    @NotEmpty(message = "Genres should not be empty")
    @NonNullElements
    private Set<Long> genreIds;
}
