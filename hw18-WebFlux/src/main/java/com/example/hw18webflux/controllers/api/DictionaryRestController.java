package com.example.hw18webflux.controllers.api;

import com.example.hw18webflux.dto.mappers.AuthorMapper;
import com.example.hw18webflux.dto.mappers.GenreMapper;
import com.example.hw18webflux.dto.response.AuthorDto;
import com.example.hw18webflux.dto.response.GenreDto;
import com.example.hw18webflux.repositories.AuthorRepository;
import com.example.hw18webflux.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DictionaryRestController {


    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    private final AuthorMapper authorMapper;

    @GetMapping("/dic/genres")
    public Flux<GenreDto> getGenresDic() {
        return genreRepository.findAll()
                .map(genreMapper::toDto);
    }

    @GetMapping("/dic/authors")
    public Flux<AuthorDto> getAuthorsDic() {
        return authorRepository.findAll()
                .map(authorMapper::toDto);
    }
}
