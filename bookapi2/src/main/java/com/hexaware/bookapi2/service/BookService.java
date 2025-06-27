package com.hexaware.bookapi2.service;

import com.hexaware.bookapi2.model.Book;
import com.hexaware.bookapi2.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> getBook(String isbn) {
        return repository.findById(isbn);
    }

    public Book addBook(Book book) {
        return repository.save(book);
    }

    public Optional<Book> updateBook(String isbn, Book updatedBook) {
        return repository.findById(isbn).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
            return repository.save(existingBook);
        });
    }

    public boolean deleteBook(String isbn) {
        if (repository.existsById(isbn)) {
            repository.deleteById(isbn);
            return true;
        } else {
            return false;
        }
    }
}
