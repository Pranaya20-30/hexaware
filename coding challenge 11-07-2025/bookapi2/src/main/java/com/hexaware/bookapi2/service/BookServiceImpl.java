package com.hexaware.bookapi2.service;

import com.hexaware.bookapi2.model.Book;
import com.hexaware.bookapi2.repository.BookRepository;
import com.hexaware.bookapi2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepo;

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepo.findById(isbn).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book updateBook(String isbn, Book book) {
        Book existing = bookRepo.findById(isbn).orElseThrow(() -> new RuntimeException("Book not found"));
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPublicationYear(book.getPublicationYear());
        return bookRepo.save(existing);
    }

    @Override
    public void deleteBook(String isbn) {
        Book book = bookRepo.findById(isbn).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepo.delete(book);
    }
}