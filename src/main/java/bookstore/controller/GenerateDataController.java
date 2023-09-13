package bookstore.controller;

import bookstore.model.Author;
import bookstore.model.AuthorRepository;
import bookstore.model.Book;
import bookstore.model.BookRepository;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1/generate")
public class GenerateDataController {

    private final Faker faker = new Faker();

    private final EntityManager entityManager;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public GenerateDataController(EntityManager entityManager, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.entityManager = entityManager;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    @Transactional
    // curl -i http://localhost:8080/api/v1/generate/random
    public GeneratedData random() {

        var numberOfBooks = faker.number().numberBetween(1, 100);
        var numberOfAuthors = faker.number().numberBetween(1, 20);

        var books = IntStream.range(1, numberOfBooks)
                .boxed()
                .map(bookNum -> bookRepository.save(Book.newBook(faker)))
                .toList();


        var authors = IntStream.range(1, numberOfAuthors)
                .boxed()
                .map(bookNum -> authorRepository.save(Author.newAuthor(faker)))
                .map(bookNum -> Author.newAuthor(faker))
                .collect(Collectors.toCollection(LinkedList::new));


        books.forEach(book -> {
            var owners = faker.number().numberBetween(1, 3);
            while (owners > 0) {
                var author = authors.removeFirst();
                author.add(book);
                authors.addLast(author);
                owners--;
            }
        });

        return new GeneratedData(numberOfAuthors, numberOfBooks);
    }
}
