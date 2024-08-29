package com.example.hw07springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private long id;

    private String title;

    private AuthorDto author;

    private List<GenreDto> genres;

    private List<CommentDto> comments = new ArrayList<>();
}
