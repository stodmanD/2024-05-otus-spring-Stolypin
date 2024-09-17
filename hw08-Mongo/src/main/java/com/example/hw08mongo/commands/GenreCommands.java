package com.example.hw08mongo.commands;

import com.example.hw08mongo.converters.GenreConverter;
import com.example.hw08mongo.services.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


import java.util.stream.Collectors;

@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    private final GenreConverter genreConverter;

    public GenreCommands (GenreService genreService, GenreConverter genreConverter) {
        this.genreConverter = genreConverter;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find genre by id", key = "fgid")
    public String findGenreById(String id) {
        return genreConverter.genreToString(genreService.findById(id));
    }
}
