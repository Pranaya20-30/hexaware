package com.hexaware.bookapi2.service;

import com.hexaware.bookapi2.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book addBook(Book book);
    Book updateBook(String isbn, Book book);
    void deleteBook(String isbn);
}