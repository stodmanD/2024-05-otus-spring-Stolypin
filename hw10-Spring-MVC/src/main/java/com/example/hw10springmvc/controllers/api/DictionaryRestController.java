package com.example.hw10springmvc.controllers.api;

import com.example.hw10springmvc.dto.response.AuthorDto;
import com.example.hw10springmvc.dto.response.GenreDto;
import com.example.hw10springmvc.services.AuthorService;
import com.example.hw10springmvc.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DictionaryRestController {
    private final GenreService genreService;

    private final AuthorService authorService;

    @GetMapping("/dic/genres")
    public List<GenreDto> getGenresDic() {
        return genreService.findAll();
    }

    @GetMapping("/dic/authors")
    public List<AuthorDto> getAuthorsDic() {
        return authorService.findAll();
    }
}
