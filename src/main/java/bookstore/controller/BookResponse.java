package bookstore.controller;

import bookstore.model.Book;

import java.util.List;

public record BookResponse(Long id, String title, String publisher, List<AuthorInfo> authors) {
    public static BookResponse of(Book book) {

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getPublisher(),
                book.getAuthors().stream().map(AuthorInfo::of)
                .toList());
    }
}
