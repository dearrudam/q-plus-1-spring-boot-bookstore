package bookstore.controller;

import bookstore.model.Author;

import java.util.List;

public record AuthorResponse(Long id, String name, List<BookResponse> books) {
    public static AuthorResponse of(Author author) {
        return new AuthorResponse(author.getId(),
                author.getName(),
                author.getBooks()
                        .stream()
                        .map(BookResponse::of)
                        .toList());
    }
}
