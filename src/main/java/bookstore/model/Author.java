package bookstore.model;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Stream;

@Entity
public class Author {

    public static Author newAuthor(Faker faker) {
        var fakerAuthor = faker.book().author();
        return newAuthor(fakerAuthor);
    }

    public static Author newAuthor(String name) {
        var author = new Author();
        author.setName(name);
        return author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new LinkedHashSet<>();


    /**
     * Don't use this constructor.
     */
    @Deprecated
    public Author() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return Set.copyOf(books);
    }

    public void add(Book... books) {
        Stream.of(books).forEach(this::add);
    }

    public void add(Book book) {
        if (this.books.add(book)) {
            book.assignTo(this);
        }
    }

    public void remove(Book... books) {
        Stream.of(books).forEach(this::remove);
    }

    public void remove(Book book) {
        if (this.books.remove(book)) {
            book.unassignedFrom(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
