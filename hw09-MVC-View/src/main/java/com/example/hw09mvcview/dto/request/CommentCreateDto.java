package com.example.hw09mvcview.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentCreateDto {

    private long id;

    @NotBlank(message = "Comment text should not be blank")
    @Size(min = 2, max = 20, message = "Comment text should have expected size")
    private String text;
}
