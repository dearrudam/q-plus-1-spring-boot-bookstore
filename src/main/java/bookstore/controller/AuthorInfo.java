package bookstore.controller;

import bookstore.model.Author;

public record AuthorInfo(Long id, String name) {
    public static AuthorInfo of(Author author) {
        return new AuthorInfo(author.getId(), author.getName());
    }
}
