package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserBooks;
import com.example.demo.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-book")
public class UserBookResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserBookResource.class);

    private LibraryService libraryService;

    @Autowired
    public UserBookResource(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<UserBooks> create(@RequestBody UserBooks userBooks ){
        LOGGER.info("Book lend call by id {}", userBooks.getBookId());
        return new ResponseEntity<>(libraryService.lend(userBooks), HttpStatus.OK);
    }

    @DeleteMapping("{userBookId}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable UUID userBookId ){
        LOGGER.info("Book return call by id {}", userBookId);
        return new ResponseEntity<>(libraryService.returnBook(userBookId), HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserBooks>> get(){
        return new ResponseEntity<>(libraryService.getAllUserBooks(), HttpStatus.OK);
    }

}
