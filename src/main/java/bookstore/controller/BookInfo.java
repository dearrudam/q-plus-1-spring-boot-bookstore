package bookstore.controller;

import bookstore.model.Book;
import bookstore.model.BookRepository;

public record BookInfo(String title, String publisher) {
    public Book toModel(BookRepository bookRepository) {
        Book book = Book.newBook(title, publisher);
        bookRepository.save(book);
        return book;
    }
}
