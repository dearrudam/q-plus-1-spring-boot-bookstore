package bookstore.controller;

import bookstore.model.Author;
import bookstore.model.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // return all persisted authors
    // curl -i http://localhost:8080/api/v1/authors
    @GetMapping
    @Transactional(readOnly = true)
    public Page<AuthorResponse> authors(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        Page<Author> authors = authorRepository.findAll(PageRequest.of(page, pageSize));
        return authors.map(AuthorResponse::of);
    }

}
