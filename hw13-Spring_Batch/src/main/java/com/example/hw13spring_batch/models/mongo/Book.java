package com.example.hw13spring_batch.models.mongo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private List<Genre> genres;

    public Book(String title, Author author, List<Genre> genres) {
        this.title = title;

        this.author = author;
        this.genres = genres;
    }
}
