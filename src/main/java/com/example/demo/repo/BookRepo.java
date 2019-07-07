package com.example.demo.repo;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {

    Iterable<Book> findByTitleContainingIgnoreCase(String name);
}
