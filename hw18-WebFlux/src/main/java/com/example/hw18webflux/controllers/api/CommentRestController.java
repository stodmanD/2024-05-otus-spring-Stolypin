package com.example.hw18webflux.controllers.api;

import com.example.hw18webflux.dto.mappers.CommentMapper;
import com.example.hw18webflux.dto.request.CommentCreateDto;
import com.example.hw18webflux.dto.response.CommentDto;
import com.example.hw18webflux.exceptions.NotFoundException;
import com.example.hw18webflux.models.Comment;
import com.example.hw18webflux.repositories.BookRepository;
import com.example.hw18webflux.repositories.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;


    @GetMapping("/comment/{id}")
    public Flux<CommentDto> getCommentsForBook(@PathVariable(name = "id") String bookId) {
        return commentRepository.findByBookId(bookId)
                .map(commentMapper::toDto);
    }

    @PostMapping("/comment/{id}")
    public Mono<CommentDto> addComment(
            @PathVariable(name = "id") String bookId,
            @RequestBody @Valid CommentCreateDto commentDto) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Book with id %s not found".formatted(bookId)))))
                .map(b -> {
                    Comment comment = new Comment();
                    comment.setText(commentDto.getText());
                    comment.setBook(b);
                    return comment;
                })
                .flatMap(commentRepository::save)
                .map(commentMapper::toDto);
    }
}
