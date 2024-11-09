package com.example.hw18webflux.controllers.api;

import com.example.hw18webflux.dto.mappers.BookMapper;
import com.example.hw18webflux.dto.mappers.CommentMapper;
import com.example.hw18webflux.dto.request.BookCreateDto;
import com.example.hw18webflux.dto.request.BookUpdateDto;
import com.example.hw18webflux.dto.response.BookDto;
import com.example.hw18webflux.exceptions.NotFoundException;
import com.example.hw18webflux.models.Author;
import com.example.hw18webflux.models.Genre;
import com.example.hw18webflux.repositories.AuthorRepository;
import com.example.hw18webflux.repositories.BookRepository;
import com.example.hw18webflux.repositories.CommentRepository;
import com.example.hw18webflux.repositories.GenreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BookRestController {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;


    @GetMapping("/book")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll(Sort.by(Sort.Order.asc("title")))
                .map(bookMapper::toDto);
    }

    @PostMapping("/book")
    public Mono<BookDto> addBook(@RequestBody @Valid BookCreateDto book) {
        Mono<Author> authorMono = authorRepository.findById(book.getAuthorId())
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Author with id %s not found".formatted(book.getAuthorId())))));
        Mono<List<Genre>> genresMono = genreRepository.findAllById(book.getGenreIds())
                .collectList()
                .flatMap(list -> {
                    if (list.size() != book.getGenreIds().size()) {
                        return Mono.create(emitter -> emitter.error(
                                new NotFoundException("Not all genres found from list %s"
                                        .formatted(book.getGenreIds()))));
                    } else {
                        return Mono.just(list);
                    }
                });

        return Mono.zip(Mono.just(book), authorMono, genresMono)
                .map(t -> bookMapper.fromDto(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(bookRepository::save)
                .map(bookMapper::toDto);
    }

    @PutMapping("/book/{id}")
    public Mono<BookDto> editBook(@RequestBody @Valid BookUpdateDto book) {
        Mono<Author> authorMono = authorRepository.findById(book.getAuthorId())
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Author with id %s not found".formatted(book.getAuthorId())))));
        Mono<List<Genre>> genresMono = genreRepository.findAllById(book.getGenreIds())
                .collectList()
                .flatMap(list -> {
                    if (list.size() != book.getGenreIds().size()) {
                        return Mono.create(emitter -> emitter.error(
                                new NotFoundException("Not all genres found from list %s"
                                        .formatted(book.getGenreIds()))));
                    } else {
                        return Mono.just(list);
                    }
                });
        Mono<BookUpdateDto> bookDtoMono = bookRepository.findById(book.getId())
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Book with id %s not found".formatted(book.getId())))))
                .flatMap(b -> Mono.just(book));

        return Mono.zip(bookDtoMono, authorMono, genresMono)
                .map(t -> bookMapper.fromDto(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(bookRepository::save)
                .map(bookMapper::toDto);
    }

    @GetMapping("/book/{id}")
    public Mono<BookDto> findBook(@PathVariable(name = "id") String bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Book with id %s not found".formatted(bookId)))))
                .map(bookMapper::toDto)
                .flatMap(b -> commentRepository.findByBookId(b.getId())
                        .map(commentMapper::toDto)
                        .collectList()
                        .map(c -> {
                            b.setComments(c);
                            return b;
                        })
                );
    }

    @DeleteMapping("/book/{id}")
    public Mono<Void> deleteBook(@PathVariable(name = "id") String bookId) {
        return commentRepository.deleteByBookId(bookId)
                .and(bookRepository.deleteById(bookId));
    }
}
