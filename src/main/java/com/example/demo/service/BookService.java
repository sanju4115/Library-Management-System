package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomExceptions;
import com.example.demo.repo.BookRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }


    public Book create(Book book){
        return bookRepo.save(book);
    }

    public List<Book> getAll(String query){
        if (query != null){
            bookRepo.findByTitleContainingIgnoreCase(query);
        }
        return bookRepo.findAll();
    }

    public Book findById(UUID id){
        Optional<Book> userOptional = bookRepo.findById(id);
        userOptional.orElseThrow(() -> new CustomExceptions.BookNotFoundException("No book found with id " + id));
        return userOptional.get();
    }


}
