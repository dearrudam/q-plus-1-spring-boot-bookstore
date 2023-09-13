package bookstore.controller;

import bookstore.model.Author;
import bookstore.model.Book;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1/generate")
public class GenerateDataController {

    private final Faker faker = new Faker();

    private final EntityManager entityManager;

    public GenerateDataController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping
    @Transactional
    public GeneratedData populate() {

        var numberOfBooks = faker.number().numberBetween(1, 100);
        var numberOfAuthors = faker.number().numberBetween(1, 20);

        var books = IntStream.range(1, numberOfBooks)
                .boxed()
                .map(bookNum -> Book.newBook(faker))
                .toList();

        books.forEach(entityManager::persist);

        var authors = IntStream.range(1, numberOfAuthors)
                .boxed()
                .map(bookNum -> Author.newAuthor(faker))
                .collect(Collectors.toCollection(LinkedList::new));

        authors.forEach(entityManager::persist);


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
