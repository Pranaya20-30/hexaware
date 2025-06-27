package com.hexaware.bookapi2.repository;

import com.hexaware.bookapi2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {}
