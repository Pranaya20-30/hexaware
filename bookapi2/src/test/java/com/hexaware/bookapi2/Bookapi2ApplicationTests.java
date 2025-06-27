package com.hexaware.bookapi2;

import com.hexaware.bookapi2.model.Book;
import com.hexaware.bookapi2.repository.BookRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Bookapi2ApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	@Order(1)
	void testSaveBook() {
		Book book = new Book("1234567890", "Java Basics", "James Gosling", 1995);
		bookRepository.save(book);

		Optional<Book> savedBook = bookRepository.findById("1234567890");
		assertTrue(savedBook.isPresent());
		assertEquals("Java Basics", savedBook.get().getTitle());
	}

	@Test
	@Order(2)
	void testFindBookById() {
		Optional<Book> book = bookRepository.findById("1234567890");
		assertTrue(book.isPresent());
		assertEquals("James Gosling", book.get().getAuthor());
	}

	@Test
	@Order(3)
	void testDeleteBook() {
		bookRepository.deleteById("1234567890");
		Optional<Book> book = bookRepository.findById("1234567890");
		assertFalse(book.isPresent());
	}
}
