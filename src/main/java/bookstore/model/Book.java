package bookstore.model;

import com.github.javafaker.Faker;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Book {

    public static Book newBook(Faker faker) {
        var fakerBook = faker.book();
        return newBook(fakerBook.title(), fakerBook.publisher());
    }

    public static Book newBook(String title, String publisher) {
        var book = new Book();
        book.setTitle(title);
        book.setPublisher(publisher);
        return book;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String publisher;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new LinkedHashSet<>();

    /**
     * Don't use this constructor.
     */
    @Deprecated
    public Book() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Set<Author> getAuthors() {
        return Set.copyOf(authors);
    }

    protected void assignTo(Author author) {
        this.authors.add(author);
    }

    protected void unassignedFrom(Author author) {
        this.authors.remove(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
