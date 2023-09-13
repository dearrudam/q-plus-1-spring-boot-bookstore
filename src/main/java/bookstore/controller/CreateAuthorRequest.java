package bookstore.controller;

import bookstore.model.Author;
import bookstore.model.AuthorRepository;
import bookstore.model.BookRepository;

import java.util.List;

public record CreateAuthorRequest(String name, List<BookInfo> books) {

    public Author toModel(AuthorRepository authorRepository, BookRepository bookRepository) {
        Author newAuthor = Author.newAuthor(this.name);
        authorRepository.save(newAuthor);
        books.stream().map(bookInfo -> bookInfo.toModel(bookRepository)).forEach(newAuthor::add);
        return newAuthor;
    }

}
