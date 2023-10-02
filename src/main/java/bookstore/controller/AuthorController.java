package bookstore.controller;

import bookstore.model.Author;
import bookstore.model.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<AuthorResponse> authors(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        Page<Author> authors = authorRepository.findAll(PageRequest.of(page, pageSize));
        return authors.map(AuthorResponse::of);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public AuthorResponse getAuthor(@PathVariable("id") Long id) {
        return authorRepository.findById(id)
                .map(AuthorResponse::of)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
