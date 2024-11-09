package com.example.hw18webflux.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
public class Author {
    @Id
    private String id;

    private String fullName;

    public Author(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Author(String fullName) {

        this.fullName = fullName;
    }

    public Author() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }




}
